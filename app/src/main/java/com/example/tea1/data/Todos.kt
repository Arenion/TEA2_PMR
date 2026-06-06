package com.example.tea1.data

interface Item {
    val name: String
}

data class TodoItem(
    override var name: String,
    var state: Boolean=false
) : Item

data class TodoList(
    override var name: String,
    val tasks: MutableList<TodoItem> = mutableListOf()
) : Item

data class UserProfile(
    val user: String,
    val lists: MutableList<TodoList> = mutableListOf()
)