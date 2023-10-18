package com.example.todo_new

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.todo_new.databinding.ActivityMainBinding
import com.example.todo_new.databinding.TodoBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), TodoAdapter.TodoInteractionListener {

    private lateinit var binding: ActivityMainBinding
    private val todoItems = mutableListOf<Todo>()
    private lateinit var mAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddTodo.isEnabled = false

        // setup list view
        binding.vrTodoItems.apply {
            mAdapter = object : TodoAdapter(todoItems, this@MainActivity) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
                    return TodoViewHolder(TodoBinding.inflate(layoutInflater, parent, false))
                }
            }
            adapter = mAdapter
        }

        // add new item
        binding.btnAddTodo.setOnClickListener {
            binding.etTodoTitle.text.toString().let {
                if (it.isNotEmpty()) {
                    todoItems.add(Todo(it, false))
                    mAdapter.notifyItemInserted(todoItems.size - 1)
                    binding.etTodoTitle.text!!.clear()
                    persist()
                }
            }
        }

        // delete completed items
        binding.btnDeleteTodo.setOnClickListener {
            todoItems.removeIf { it.isChecked }
            mAdapter.notifyDataSetChanged()
            persist()
        }

        binding.etTodoTitle.addTextChangedListener {
            binding.btnAddTodo.isEnabled = !it.isNullOrEmpty() && it.toString().length <= 20
            binding.tilTodoInput.helperText = if (it.toString().length > 20) "Max 20 chars" else ""
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // loads items
        load()
    }

    // reads shared preferences and loads items
    private fun load() {
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        prefs.getString("items", null)?.let {
            val items = Gson().fromJson(it, Array<Todo>::class.java)
            todoItems.addAll(items)
            mAdapter.notifyItemRangeInserted(0, items.size)
        }
    }

    // persists item states in shared preferences
    override fun persist() {
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        prefs.edit().apply {
            putString("items", Gson().toJson(todoItems.toTypedArray()))
            apply()
        }
    }
}