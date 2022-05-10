package com.example.todoapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.example.todoapp.data.Task
import com.example.todoapp.util.AppDataBase
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class edittaskall : AppCompatActivity(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    var day = 0
    var mounth = 0
    var year = 0
    var hour = 0
    var minute = 0
    var Savedday = 0
    var Savedmounth = 0
    var Savedyear = 0
    var Savedhour = 0
    var Savedminute = 0

    var typestring = "type"
    var dateString = "date"
    private var checkBoxPersonal: CheckBox? = null
    private var checkBoxWork: CheckBox? = null
    private var checkBoxSchool: CheckBox? = null
    lateinit var mSharedPref: SharedPreferences
    lateinit var txtName: TextInputEditText
    lateinit var txtLayoutName: TextInputLayout

    lateinit var txtDeadline: TextInputEditText
    lateinit var txtLayoutDeadline: TextInputLayout

    lateinit var txtDescription: TextInputEditText
    lateinit var txtLayoutDescription: TextInputLayout

    lateinit var btnSave: Button
    lateinit var mainIntent : Intent
    lateinit var dataBase: AppDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edittaskall)
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        toolbar.setNavigationOnClickListener {

            val  mainIntent = Intent(this, TaskEdit::class.java)
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
        checkBoxPersonal = findViewById(R.id.cbPersonal)
        checkBoxWork = findViewById(R.id.cbWork)
        checkBoxSchool = findViewById(R.id.cbSchool)
        btnSave= findViewById(R.id.btnSave)

        val c = Calendar.getInstance()

        val nowyear = c.get(Calendar.YEAR)
        val  nowmonth = c.get(Calendar.MONTH)
        val  nowday = c.get(Calendar.DAY_OF_MONTH)

        val  nowhour = c.get(Calendar.HOUR_OF_DAY)
        val  nowminute = c.get(Calendar.MINUTE)


        txtName = findViewById(R.id.txtName)
        txtLayoutName = findViewById(R.id.txtLayoutName)

        txtDeadline = findViewById(R.id.txtDeadline)
        txtLayoutDeadline = findViewById(R.id.txtLayoutDeadline)

        txtDescription = findViewById(R.id.txtDescription)
        txtLayoutDescription = findViewById(R.id.txtLayoutDescription)
        txtDeadline.setFocusable(false)
        txtDeadline.setText("$nowhour:$nowminute $nowday/$nowmonth/$nowyear")
        txtDeadline.setOnClickListener {

            pickdate()
        }
        txtName.setText(tasknameesit)
        txtDeadline.setText(Taskdeadlineesit)
        txtDescription.setText(Taskdescriptionesit)

        btnSave!!.setOnClickListener {

            txtLayoutDeadline!!.error = null
            txtLayoutDescription!!.error = null
            txtLayoutName!!.error = null




            if (txtName?.text!!.isEmpty()) {
                txtLayoutName!!.error = "must not be empty"
                return@setOnClickListener
            }
            if (txtDeadline?.text!!.isEmpty()) {
                txtLayoutDeadline!!.error = "must not be empty"
                return@setOnClickListener
            }

            if (txtDescription?.text!!.isEmpty()) {
                txtLayoutDescription!!.error = "must not be empty"
                return@setOnClickListener
            }


            if(!checkBoxPersonal!!.isChecked && !checkBoxWork!!.isChecked && !checkBoxSchool!!.isChecked){
                Toast.makeText(this@edittaskall, "Check Type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(checkBoxPersonal!!.isChecked){
                Tasktypeeesit= "Personal"
            }
            if(checkBoxWork!!.isChecked){
                Tasktypeeesit= "Work"
            }
            if(checkBoxSchool!!.isChecked){
                Tasktypeeesit = "School"
            }
            dateString = "$Savedhour:$Savedminute $Savedday/$Savedmounth/$Savedyear"

            AppDataBase.getDatabase(this.applicationContext).taskdao().update(
                Task(Taskidsesit, txtName?.text.toString(),dateString,txtDescription?.text.toString(),Tasktypeeesit,Taskstatusesit)
            )
            Toast.makeText(this@edittaskall, "Task Edit", Toast.LENGTH_SHORT).show()


        }
    }
    private fun pickdate(){

        getDateTimeCalender()
        DatePickerDialog(this,this,year,mounth,day).show()
    }
    private fun getDateTimeCalender(){

        val cal =Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        mounth = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

        Savedday= p3
        Savedmounth = p2
        Savedyear = p1
        getDateTimeCalender()
        TimePickerDialog(this,this,hour,minute,true).show()

    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {


        Savedhour = p1
        Savedminute = p2
        txtDeadline.setText("$Savedhour:$Savedminute $Savedday/$Savedmounth/$Savedyear")

    }
}