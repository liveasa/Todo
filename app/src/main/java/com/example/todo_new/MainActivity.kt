package com.example.todo_new

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_new.databinding.ActivityMainBinding
import com.example.todo_new.databinding.TodoBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID

class MainActivity : AppCompatActivity(), TodoAdapter.TodoInteractionListener, Callback<Array<Todos>> {

    private lateinit var binding: ActivityMainBinding
    private val todoItems = mutableListOf<Todos>()
    private lateinit var mAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // disable add button
        binding.btnAddTodo.isEnabled = false

        // setup list view
        binding.vrTodoItems.apply {
            // setup adapter
            mAdapter = object : TodoAdapter(todoItems, this@MainActivity) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
                    return TodoViewHolder(TodoBinding.inflate(layoutInflater, parent, false))
                }
            }
            adapter = mAdapter

            // setup swipe touch listener
            ItemTouchHelper(object : ItemTouchHelper.Callback() {
                override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                    return makeMovementFlags(0, ItemTouchHelper.LEFT)
                }

                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    deleteTodos((viewHolder as TodoAdapter.TodoViewHolder).getItem().id!!)
                }
            }).attachToRecyclerView(this)
        }

        // add new item
        binding.btnAddTodo.setOnClickListener {
            binding.etTodoTitle.text.toString().let {
                if (it.isNotEmpty()) {
                    // add todos
                    addTodo(it)
                    // clear the text input
                    binding.etTodoTitle.text?.clear()
                }
            }
        }

        // setup text watcher
        binding.etTodoTitle.addTextChangedListener {
            // disable the button if text is null, empty or the length is greater than 20
            binding.btnAddTodo.isEnabled = !it.isNullOrEmpty() && it.toString().length <= 20
            // setup helper text
            binding.tilTodoInput.helperText = if (it.toString().length > 20) "Max 20 chars" else ""
        }
    }

    // will be called upon activity creation
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // loads items
        load()
    }

    // loads todos
    private fun load() {
        RestFulWebService.getWebService().getAllTodos().enqueue(this)
    }

    // add todos
    private fun addTodo(todo: String) {
        RestFulWebService.getWebService().createTodo(Todos(UUID.randomUUID().toString(), todo, false)).enqueue(this)
    }

    // update todos
    override fun updateTodo(id: String, data: Todos) {
        RestFulWebService.getWebService().updateTodo(id, data).enqueue(this)
    }

    // delete todos
    private fun deleteTodos(id: String) {
        RestFulWebService.getWebService().deleteTask(id).enqueue(this)
    }

    // handle todos responses
    override fun onResponse(call: Call<Array<Todos>>, response: Response<Array<Todos>>) {
        // check if the response is success
        if (response.isSuccessful) {
            response.body()?.let {
                // clear existing items from the list view
                todoItems.clear()
                // add items
                todoItems.addAll(it)
                // notify list view adapter to re-render list view items
                mAdapter.notifyDataSetChanged()
                return
            }
        }
    }

    override fun onFailure(call: Call<Array<Todos>>, t: Throwable) {
        // displays any error messages
        Snackbar.make(binding.root, t.message!!, Snackbar.LENGTH_LONG).show()
    }
}