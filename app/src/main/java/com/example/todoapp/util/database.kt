package com.example.todoapp.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.dao.TaskDao
import com.example.todoapp.data.Task


@Database(entities = [Task::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun taskdao(): TaskDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            if (instance == null) {
                synchronized(this) {
                    instance =
                        Room.databaseBuilder(context, AppDataBase::class.java, "todoappbase")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return instance!!
        }
    }
}
