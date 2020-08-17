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
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.*
import kotlinx.android.synthetic.main.row.view.*
import kotlinx.coroutines.*
import java.util.*


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
        // 8月17日修正　var は使わないようにする。この行は、取り合えず何かを表示させるためのものだったのでコメントアウト
//        var todo = mutableMapOf<String,String>("date" to "2020/07/01", "subject" to "大阪でイベント","detail" to  "コロナリスク回避のため中止になるかも、コロナリスク回避のため中止になるかも、コロナリスク回避のため中止になるかも、コロナリスク回避のため中止になるかも、コロナリスク回避のため中止になるかも")
//        todoList.add(todo)

        // 8月17日修正　buildを毎回呼ばないようにしてみる。　dbがnullの時だけbuildを呼び出す
        var db : AppDatabase? = null
        if(db ===  null){
            db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "todoList").build()
        }
        val todoEntity = ToDoEntity()
        todoEntity.todoId = Random().nextLong()
        //後で入力用のアクティビティから入力値をもってくる
        todoEntity.date = "2020/08/17"
        todoEntity.subject = "姫路へドライブ"
        todoEntity.detail = "暑さで中止になるかも。熱中症に要注意。"
        GlobalScope.launch{
            //8月17日修正　”insertとfetchを一緒に行うことは通常ありません”　　データは何件か入ったのでinsertは一旦コメントアウト
//            db.ToDoDao().insert(todoEntity)
            var sqlList = db.ToDoDao().getAll()
            sqlList.forEach { elements ->
                val todo = mutableMapOf<String,String>("date" to elements.date, "subject" to elements.subject, "detail" to elements.detail)
                todoList.add(todo)
            }
        }
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