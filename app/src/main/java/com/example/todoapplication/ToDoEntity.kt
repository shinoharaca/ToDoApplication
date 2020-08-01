package com.example.todoapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todoList")
class ToDoEntity {
    @PrimaryKey
    var todo_id : Long = 0

    @ColumnInfo
    var date : String = ""

    @ColumnInfo
    var subject : String = ""

    @ColumnInfo
    var detail : String = ""
}