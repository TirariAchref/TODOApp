package com.example.todoapp


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.androidplot.pie.PieChart
import com.androidplot.pie.Segment
import com.androidplot.pie.SegmentFormatter
import com.example.todoapp.data.Task
import com.example.todoapp.util.AppDataBase


class graphFragment : Fragment() {
    lateinit var taskList : MutableList<Task>
    lateinit var Piechart: PieChart
    lateinit var dataBase: AppDataBase
    lateinit var TaskTotal: TextView
    lateinit var TaskTODO: TextView
    lateinit var TaskpROG: TextView
    lateinit var TaskDone: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_graph, container, false)
      Piechart = rootView.findViewById(R.id.pieChart)
        dataBase = AppDataBase.getDatabase(requireActivity())
        TaskTotal = rootView.findViewById(R.id.Total)
        TaskTODO = rootView.findViewById(R.id.ToDo)
        TaskpROG = rootView.findViewById(R.id.InProgress)
        TaskDone = rootView.findViewById(R.id.Done)
        taskList = dataBase.taskdao().getAllTasks()
        var n1 = 0
        var n2 = 0
        var n3 = 0

        for (a in taskList){

            if(a.status == "To Do"){
                n1 += 1
            }else if(a.status == "In Progress"){
                n2 += 1
            }else if (a.status == "Done"){
                n3 += 1
            }

        }
        val nt= n1+n2+n3
        val s1= Segment("", n1.toInt())
        val s2=Segment( "",n2.toInt())
        val s3=Segment(  "", n3.toInt())

        val sf1= SegmentFormatter(Color.RED)
        val sf2=SegmentFormatter(Color.BLUE)
        val sf3=SegmentFormatter(Color.GREEN)
        TaskTotal.text =  "Total : ${nt.toString()}"
        TaskTODO.text ="To DO : ${n1.toString()}"
        TaskpROG.text = "In Progress : ${n2.toString()}"
        TaskDone.text = "Done : ${n3.toString()}"
   Piechart.textAlignment
        Piechart.addSegment(s1,sf1)
        Piechart.addSegment(s2,sf2)
        Piechart.addSegment(s3,sf3)

        return rootView
    }


}