package com.example.todoapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todoList")
data class ToDoEntity(
    @PrimaryKey
    val todoId : Long ,

    @ColumnInfo
    val date : String ,

    @ColumnInfo
    val subject : String ,

    @ColumnInfo
    val detail : String
)

