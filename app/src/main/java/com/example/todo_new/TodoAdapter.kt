package com.example.todo_new

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_new.databinding.TodoBinding

abstract class TodoAdapter(private val todos: MutableList<Todo>, private val mListener: TodoInteractionListener) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(private val view: TodoBinding) : RecyclerView.ViewHolder(view.root) {

        // populate the item
        fun bind(item: Todo, listener: TodoInteractionListener) {
            view.tvToDoTit.text = item.title
            view.cbDone.isChecked = item.isChecked
            strikeThrough(item.isChecked)
            view.cbDone.setOnCheckedChangeListener { _, checked ->
                item.isChecked = checked
                strikeThrough(checked)
                listener.persist()
            }
        }

        // make the text strike through if the item checked
        private fun strikeThrough(checked: Boolean) {
            if (checked) {
                view.tvToDoTit.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                view.tvToDoTit.paintFlags = 0
            }
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.bind(curTodo, mListener)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    interface TodoInteractionListener {
        fun persist()
    }
}