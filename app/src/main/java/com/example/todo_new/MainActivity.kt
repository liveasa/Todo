package com.example.todo_new

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_new.databinding.ActivityMainBinding
import com.example.todo_new.databinding.TodoBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val todoItems = mutableListOf<Todo>()
    private lateinit var mAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vrTodoItems.apply {
            mAdapter = object : TodoAdapter(todoItems) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
                    return TodoViewHolder(TodoBinding.inflate(layoutInflater, parent, false))
                }
            }
            adapter = mAdapter
        }

        binding.btnAddTodo.setOnClickListener {
            binding.etTodoTitle.text.toString().let {
                if (it.isNotEmpty()) {
                    todoItems.add(0, Todo(it, false))
                    mAdapter.notifyItemInserted(0)
                    binding.etTodoTitle.text!!.clear()
                }
            }
        }
    }
}