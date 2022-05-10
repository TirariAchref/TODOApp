package com.example.todoapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.todoapp.data.Task
import com.example.todoapp.util.AppDataBase

class TaskEdit : AppCompatActivity() {
    lateinit var Taskname: TextView
    lateinit var Taskdeadline: TextView
    lateinit var Taskdescription: TextView
    lateinit var Tasktype: TextView
    lateinit var Taskstatus: TextView
    lateinit var mSharedPref: SharedPreferences
    private lateinit var btnchange: Button
    private lateinit var btnedit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_edit)
        btnchange = findViewById(R.id.Change)
        btnedit = findViewById(R.id.EditChange)
        Taskname = findViewById(R.id.NameTask)
        Taskdeadline = findViewById(R.id.DeadlineTask)
        Taskdescription = findViewById(R.id.descriptionTask)
        Tasktype = findViewById(R.id.typeTask)
        Taskstatus = findViewById(R.id.statusTask)

        val toolbar: Toolbar = findViewById(R.id.toolbarback)
        setSupportActionBar(toolbar)


        toolbar.setNavigationOnClickListener {

            val  mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }

        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        var tasknameesit =  mSharedPref.getString(nameEdit, "").toString()
        var Taskdeadlineesit =  mSharedPref.getString(deadlineEdit, "").toString()
        var Taskdescriptionesit =  mSharedPref.getString(descriptionEdit, "").toString()
        var Tasktypeeesit = mSharedPref.getString(typeEdit, "").toString()
        var Taskstatusesit =   mSharedPref.getString(statusEdit, "").toString()
        var Taskidsesit =   mSharedPref.getInt(IdEdit, 0)
        btnedit.setVisibility(View.GONE);
        Taskname.text =  "Name : $tasknameesit"
        Taskdeadline.text ="Deadline : $Taskdeadlineesit"
        Taskdescription.text = "Description : $Taskdescriptionesit"
        Tasktype.text = "Type : $Tasktypeeesit"
        Taskstatus.text = "Status : $Taskstatusesit"
        if (Taskstatusesit == "In Progress") {

            btnedit.setVisibility(View.VISIBLE);
        }
        btnedit.setOnClickListener {
           val mainIntent = Intent(this, edittaskall::class.java)
            startActivity(mainIntent)
            finish()
        }
        btnchange.setOnClickListener {
            if(Taskstatusesit =="To Do"){
                Taskstatusesit = "In Progress"
                Toast.makeText(this@TaskEdit, "Status Changed to In Progres", Toast.LENGTH_SHORT).show()
                Taskstatus.text = "Status : $Taskstatusesit"
                btnedit.setVisibility(View.VISIBLE);
            }else if(Taskstatusesit =="In Progress"){
                Toast.makeText(this@TaskEdit, "Status Changed to Done", Toast.LENGTH_SHORT).show()
                btnedit.setVisibility(View.GONE);
                Taskstatusesit = "Done"
                Taskstatus.text = "Status : $Taskstatusesit"
            }else if(Taskstatusesit =="Done"){
                btnedit.setVisibility(View.GONE);
                Toast.makeText(this@TaskEdit, "Status Already Done", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            AppDataBase.getDatabase(this.applicationContext).taskdao().update(
                Task(Taskidsesit,tasknameesit,Taskdeadlineesit,Taskdescriptionesit,Tasktypeeesit,Taskstatusesit)

            )
        }
    }
}