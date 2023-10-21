package com.example.todo_new

import com.google.gson.annotations.SerializedName

data class Todos(
    @SerializedName("id") var id: String? = null,
    @SerializedName("todo") var todo: String? = null,
    @SerializedName("completed") var completed: Boolean? = null
)