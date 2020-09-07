package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.view.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val recyclerView = findViewById<RecyclerView>(R.id.todoList) findViewByIdでなくても大丈夫
        val recyclerView = todoList
        val layout = LinearLayoutManager(this)
        recyclerView.layoutManager = layout
        lifecycleScope.launch {

            val sqlList = makeToDoList()
            //抽出したリストに対して、日付、件名、詳細のキーをmutableMapで対応させたうえでtodoListにマッピング
            val todoList = sqlList.map{ mutableMapOf("date" to it.date, "subject" to it.subject, "detail" to it.detail) }

            val adapter = RecycleAdapter(todoList)
            recyclerView.adapter = adapter
        }
    }

    private suspend fun makeToDoList():List<ToDoEntity> {

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "todoList").build()
        return db.ToDoDao().getAll()

    }

    private inner class RecyclerTodoViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        //private val itemView: View = itemView  エラーになってしまう
    }

    private inner class RecycleAdapter(private val toDoListData:List<ToDoEntity>):RecyclerView.Adapter<RecyclerTodoViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerTodoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.row,parent,false)
            val holder = RecyclerTodoViewHolder(view)
            return holder
        }

        override fun getItemCount(): Int {
            return toDoListData.size
        }

        override fun onBindViewHolder(holder: RecyclerTodoViewHolder, position: Int) {
            val toDoItem = toDoListData[position]
            val date = toDoItem["date"]
            val subject = toDoItem["subject"]
            val detail = toDoItem["detail"]

            holder.itemView.tvDate.text = date
            holder.itemView.tvSubject.text = subject
            holder.itemView.tvDetail.text = detail
        }
    }
}