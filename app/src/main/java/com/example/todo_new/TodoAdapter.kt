package com.example.todo_new

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_new.databinding.TodoBinding

abstract class TodoAdapter(private val todos: MutableList<Todos>, private val mListener: TodoInteractionListener) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(private val view: TodoBinding) : RecyclerView.ViewHolder(view.root) {

        private lateinit var mItem: Todos

        fun getItem() = mItem

        // populate the item
        fun bind(item: Todos, listener: TodoInteractionListener) {
            mItem = item
            view.tvToDoTit.text = item.todo
            view.cbDone.isChecked = item.completed!!
            strikeThrough(item.completed!!)
            view.cbDone.setOnCheckedChangeListener { _, checked ->
                strikeThrough(checked)
                listener.updateTodo(item.id!!, item.apply { completed = checked })
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
        fun updateTodo(id: String, data: Todos)
    }
}