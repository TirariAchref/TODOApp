package com.example.todoapp.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val taskpic : ImageView

    val taskName : TextView
    val tasldescr : TextView
    val taskdeadline : TextView
    val taskStatus : TextView


    init {
        taskpic = itemView.findViewById<ImageView>(R.id.picc)

        taskName = itemView.findViewById<TextView>(R.id.name)
        tasldescr = itemView.findViewById<TextView>(R.id.description)
        taskdeadline = itemView.findViewById<TextView>(R.id.deadline)
        taskStatus = itemView.findViewById<TextView>(R.id.status)
    }

}