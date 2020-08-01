package com.example.todoapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ToDoDao {
    @Query("SELECT * FROM todoList" )
    fun getAll(): List<ToDoEntity>

    @Insert
    fun insert(toDoEntity: ToDoEntity)

    @Delete
    fun delete(toDoEntity: ToDoEntity)
}