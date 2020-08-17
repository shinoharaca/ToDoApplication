package com.example.todoapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todoList")
/* 8月17日修正　data class ,コンストラクタで初期化, しかし、プロパティをvalにしたいがうまくできない,
 val にするとMainActivity.kt の todoEntity.date 他に下赤波線"Val cannot be reassingned"がでる*/
data class ToDoEntity(
    @PrimaryKey
    var todoId : Long = 0,

    @ColumnInfo
    var date : String = "",

    @ColumnInfo
    var subject : String = "",

    @ColumnInfo
    var detail : String = ""
)
{

}
