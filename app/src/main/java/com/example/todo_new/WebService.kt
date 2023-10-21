package com.example.todo_new

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// webservice definition
interface WebService {

    // getAllTodos - fetch todos from the server
    @GET("todos")
    fun getAllTodos(): Call<Array<Todos>>

    // createTodo - create todos in server and returns an array of todos
    @POST("todos/add")
    fun createTodo(@Body task: Todos): Call<Array<Todos>>

    // updateTodo - updates todos in the server and returns an array of todos
    @PUT("todos/{id}")
    fun updateTodo(@Path("id") id: String, @Body updated: Todos): Call<Array<Todos>>

    // deleteTask - deletes todos in the server and returns an array of todos
    @DELETE("todos/{id}")
    fun deleteTask(@Path("id") id: String): Call<Array<Todos>>
}