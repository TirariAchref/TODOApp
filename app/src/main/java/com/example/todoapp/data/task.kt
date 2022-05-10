package com.example.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "deadline")
    val deadline: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "status")
    val status: String,


    )