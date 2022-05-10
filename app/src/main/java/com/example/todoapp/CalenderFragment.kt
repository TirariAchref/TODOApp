package com.example.todoapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.adapter.TasKAdapter
import com.example.todoapp.data.Task
import com.example.todoapp.util.AppDataBase


class CalenderFragment : Fragment() {

    lateinit var taskRecyclerView: RecyclerView
    lateinit var taskAdapter: TasKAdapter

    lateinit var taskList : MutableList<Task>
    val taskListvalider : MutableList<Task> =arrayListOf()
    lateinit var dataBase: AppDataBase
    lateinit var emptyView: TextView

    lateinit var mCalendarView: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_calender, container, false)


        dataBase = AppDataBase.getDatabase(requireActivity())

        taskRecyclerView = rootView.findViewById(R.id.taskrecyclerviewCalender)
        emptyView = rootView.findViewById(R.id.empty_viewCalender)

        taskList = dataBase.taskdao().getAllTasks()
        mCalendarView = rootView.findViewById(R.id.calendarView)
        mCalendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "$dayOfMonth/$month/$year"
            taskListvalider.clear()

            for (a in taskList){
                val domain: String? = a.deadline.substringAfterLast(" ")
                if(domain == date){
                  taskListvalider.add(a)
                }

            }
            if (taskListvalider.isEmpty()) {
                taskRecyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
            else {
                taskRecyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
            taskAdapter = TasKAdapter(taskListvalider)

            taskRecyclerView.adapter = taskAdapter

            taskRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
        }

        if (taskListvalider.isEmpty()) {
            taskRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            taskRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }




        return rootView
    }

}