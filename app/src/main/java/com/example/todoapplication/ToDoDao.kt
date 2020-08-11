package com.example.todoapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ToDoDao {
    @Query("SELECT * FROM todoList" )
    suspend fun getAll(): List<ToDoEntity>

    @Insert
    suspend fun insert(toDoEntity: ToDoEntity)

    @Delete
    suspend fun delete(toDoEntity: ToDoEntity)
}