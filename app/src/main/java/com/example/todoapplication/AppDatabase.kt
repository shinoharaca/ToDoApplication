package com.example.todoapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ToDoEntity::class), version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun ToDoDao(): ToDoDao
}