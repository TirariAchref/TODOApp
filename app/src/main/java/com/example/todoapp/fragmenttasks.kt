package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.adapter.TasKAdapter
import com.example.todoapp.data.Task
import com.example.todoapp.util.AppDataBase

class fragmenttasks : Fragment() {

    lateinit var taskRecyclerView: RecyclerView
    lateinit var taskAdapter:TasKAdapter
    lateinit var Tasktype: TextView
    lateinit var taskList : MutableList<Task>
     val taskListvalider : MutableList<Task> =arrayListOf()
    lateinit var dataBase: AppDataBase
    lateinit var emptyView: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_fragmenttasks, container, false)
        dataBase = AppDataBase.getDatabase(requireActivity())
        Tasktype = rootView.findViewById(R.id.textTask)
        taskRecyclerView = rootView.findViewById(R.id.taskrecyclerview)
        emptyView = rootView.findViewById(R.id.empty_view)

        taskList = dataBase.taskdao().getAllTasks()
        taskListvalider.clear()
        val type = requireArguments().getString("type","NULL")
        Tasktype.text = type
        for (a in taskList){

            if(a.type == type){

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

        return rootView
    }
    companion object {

        @JvmStatic
        fun newInstance(type: String) =
            fragmenttasks().apply {
                arguments = Bundle().apply {
                    putString("type", type)


                }
            }
    }


}