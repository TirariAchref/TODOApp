package com.example.todoapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import com.example.todoapp.data.Task
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.util.AppDataBase
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class addTask : AppCompatActivity(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
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

    lateinit var txtName: TextInputEditText
    lateinit var txtLayoutName: TextInputLayout

    lateinit var txtDeadline: TextInputEditText
    lateinit var txtLayoutDeadline: TextInputLayout

    lateinit var txtDescription: TextInputEditText
    lateinit var txtLayoutDescription: TextInputLayout

    lateinit var btnSave: Button
    lateinit var mainIntent : Intent
    lateinit var dataBase: AppDataBase


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_task)
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        toolbar.setNavigationOnClickListener {

              mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
        createNotificationChannel()
        checkBoxPersonal = findViewById(R.id.cbPersonal)
        checkBoxWork = findViewById(R.id.cbWork)
        checkBoxSchool = findViewById(R.id.cbSchool)
        btnSave= findViewById(R.id.btnSave)

        val c = Calendar.getInstance()

         Savedyear = c.get(Calendar.YEAR)
          Savedmounth = c.get(Calendar.MONTH)
          Savedday = c.get(Calendar.DAY_OF_MONTH)

          Savedhour = c.get(Calendar.HOUR_OF_DAY)
          Savedminute = c.get(Calendar.MINUTE)


        txtName = findViewById(R.id.txtName)
        txtLayoutName = findViewById(R.id.txtLayoutName)

        txtDeadline = findViewById(R.id.txtDeadline)
        txtLayoutDeadline = findViewById(R.id.txtLayoutDeadline)

        txtDescription = findViewById(R.id.txtDescription)
        txtLayoutDescription = findViewById(R.id.txtLayoutDescription)
        txtDeadline.setFocusable(false)
        txtDeadline.setText("$Savedhour:$Savedminute $Savedday/$Savedmounth/$Savedyear")
        txtDeadline.setOnClickListener {

            pickdate()
        }
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
                Toast.makeText(this@addTask, "Check Type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(checkBoxPersonal!!.isChecked){

                typestring= "Personal"
            }
            if(checkBoxWork!!.isChecked){

                typestring= "Work"
            }
            if(checkBoxSchool!!.isChecked){

                typestring = "School"
            }
            dateString = "$Savedhour:$Savedminute $Savedday/$Savedmounth/$Savedyear"

            AppDataBase.getDatabase(this.applicationContext).taskdao().insert(
                Task(0,name = txtName?.text.toString(), deadline = dateString,description = txtDescription?.text.toString(),type = typestring, status = "To Do")
            )
            Toast.makeText(this@addTask, "Task Aded", Toast.LENGTH_SHORT).show()
            scheduleNotification()

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = txtName?.text.toString()
        val message = txtDescription?.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
    }
    private fun showAlert(time: Long, title: String, message: String)
    {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }
    private fun getTime(): Long
    {
        val minute = Savedminute
        val hour = Savedhour
        val day = Savedday
        val month = Savedmounth
        val year = Savedyear

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        calendar.add(Calendar.HOUR, -1);
        return calendar.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
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