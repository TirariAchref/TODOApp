package com.example.todoapp.adapter


import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.*
import com.example.todoapp.data.Task
import com.example.todoapp.viewholder.TaskViewHolder


class TasKAdapter(val taskList: MutableList<Task>) : RecyclerView.Adapter<TaskViewHolder>() {
    lateinit var mSharedPref: SharedPreferences
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemtask, parent, false)

        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val name = taskList[position].name
        val description = taskList[position].description
        val deadline = taskList[position].deadline
        val type = taskList[position].type
        val id = taskList[position].id
        val status = taskList[position].status
        if(type == "Personal"){
            holder.taskpic.setImageResource(R.drawable.personaltask)
        }else if(type == "Work")
        {
            holder.taskpic.setImageResource(R.drawable.worktask)
        }else if(type == "School"){
            holder.taskpic.setImageResource(R.drawable.schooltask)
        }


        holder.taskName.text = name
        holder.taskdeadline.text = deadline
        holder.tasldescr.text = description
        holder.taskStatus.text = status
holder.itemView.setOnClickListener{ v ->
    mSharedPref = v.context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
    mSharedPref.edit().apply {
        putInt(IdEdit,id)
        putString(nameEdit, name)
        putString(descriptionEdit, description)
        putString(deadlineEdit, deadline)
        putString(typeEdit, type)
        putString(statusEdit, taskList[position].status)
    }.apply()
    val intent = Intent(v.context, TaskEdit::class.java)
    v.context.startActivity(intent)

}



    }

    override fun getItemCount() = taskList.size
}