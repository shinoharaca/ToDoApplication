package com.example.todoapplication

import android.app.Application
import androidx.room.Room
import kotlin.random.Random

class ToDoApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java,"todoList").build()

        val todo = ToDoEntity()
        todo.todo_id = Random.nextLong()
        todo.date = "2020/08/01"
        todo.subject = "神戸でランニング"
        todo.detail = "1時間のランニング予定だが、コロナで中止になるかもしれない。"

        db.ToDoDao().insert(todo)
    }

}