package com.example.todo_new

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_new.databinding.TodoBinding

abstract class TodoAdapter(private val todos: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(itemView: TodoBinding) : RecyclerView.ViewHolder(itemView.root)

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}