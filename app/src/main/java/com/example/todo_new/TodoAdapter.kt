package com.example.todo_new

import androidx.recyclerview.widget.RecyclerView
import com.example.todo_new.databinding.TodoBinding

abstract class TodoAdapter(private val todos: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(private val view: TodoBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: Todo) {
            view.tvToDoTit.text = item.title
            view.cbDone.isChecked = item.isChecked
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.bind(curTodo)
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}