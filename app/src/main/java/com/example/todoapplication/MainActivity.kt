package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.*
import kotlinx.android.synthetic.main.row.view.*
import java.util.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainView = findViewById<RecyclerView>(R.id.todoList)
        val layout = LinearLayoutManager(applicationContext)
        mainView.layoutManager = layout

        val todoList = makeToDoList()

        val adapter = RecycleAdapter(todoList)
        mainView.adapter = adapter

    }

    //todoリストのデータ作成　
    private fun makeToDoList():MutableList<MutableMap<String,String>>{
        val todoList = mutableListOf<MutableMap<String,String>>()
        //後でsqlか何かのデータベースに蓄積したデータをforeachで出力させる
        var todo = mutableMapOf<String,String>("date" to "2020/07/01", "subject" to "大阪でイベント","detail" to  "コロナリスク回避のため中止になるかも、コロナリスク回避のため中止になるかも、コロナリスク回避のため中止になるかも、コロナリスク回避のため中止になるかも、コロナリスク回避のため中止になるかも")
        todoList.add(todo)
        return todoList
    }

    private inner class RecyclerTodoViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
//        private val itemView: View = itemView  エラーになってしまう
    }

    private inner class RecycleAdapter(private val toDoListData:MutableList<MutableMap<String,String>>):RecyclerView.Adapter<RecyclerTodoViewHolder>(){
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