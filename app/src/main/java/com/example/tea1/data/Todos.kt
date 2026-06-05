package com.example.tea1.data

import kotlin.uuid.Uuid

data class TodoItem(
    val state: Boolean,
    val task: String
)

data class TodoList(
    val name: String,
    val tasks: MutableList<TodoItem> = mutableListOf()
)

data class UserTodos(
    val user: String,
    val lists: MutableList<TodoList> = mutableListOf()
)