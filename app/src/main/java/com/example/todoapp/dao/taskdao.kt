package com.example.todoapp.dao

import androidx.room.*
import com.example.todoapp.data.Task

@Dao
interface TaskDao {
    @Insert
    fun insert(edc: Task)

    @Update
    fun update(edc: Task)

    @Delete
    fun delete(edc: Task)

    @Query("SELECT * FROM task")
    fun getAllTasks(): MutableList<Task>
}