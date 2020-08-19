package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.row.view.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainView = findViewById<RecyclerView>(R.id.todoList)
        val layout = LinearLayoutManager(applicationContext)
        mainView.layoutManager = layout

        //8月19日　追加 コルーチンをここで使用
        val todoList =  mutableListOf<MutableMap<String,String>>()
        GlobalScope.launch {
            val sqlList = async { makeToDoList() }.await()
            sqlList.forEach { elements ->
                var todo = mutableMapOf<String,String>("date" to elements.date, "subject" to elements.subject, "detail" to elements.detail)
                todoList.add(todo)
            }
        }
        val adapter = RecycleAdapter(todoList)
        mainView.adapter = adapter
    }

    //todoリストのデータ作成　8月19日　suspend に修正　
        private suspend fun makeToDoList():List<ToDoEntity> {
//        val todoList = mutableListOf<MutableMap<String,String>>()　　使わなくなったのでコメントアウト
//        var todo = mutableMapOf<String,String>()　　　使わなくなったのでコメントアウト
        var db : AppDatabase? = null
        if(db ===  null){
            db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "todoList").build()
        }
//        val todoEntity = ToDoEntity(Random().nextLong(),"2020/08/18","浜松へドライブ","最高気温になっている")　変数todoEntityは　不要になったのでコメントアウト
//        GlobalScope.launch{
//        8月17日修正　”insertとfetchを一緒に行うことは通常ありません”　　データは何件か入ったのでinsertは一旦コメントアウト
//        db.ToDoDao().insert(todoEntity)
//            sqlList.forEach { elements ->     使わなくなったのでコメントアウト
//              todo = mutableMapOf<String,String>("date" to elements.date, "subject" to elements.subject, "detail" to elements.detail)
//              todoList.add(todo)
//                }
//            }
         return db.ToDoDao().getAll()
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