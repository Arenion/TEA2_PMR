package com.example.tea1.data

import kotlin.uuid.Uuid

interface Item {
    val name: String
}

data class TodoItem(
    override val name: String,
    val state: Boolean
) : Item

data class TodoList(
    override val name: String,
    val tasks: MutableList<TodoItem> = mutableListOf()
) : Item

data class UserTodos(
    val user: String,
    val lists: MutableList<TodoList> = mutableListOf()
)