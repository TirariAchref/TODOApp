package com.example.todoapp

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
const val PREF_NAME = "DATA_CV_PREF"
const val IdEdit = "id"
const val nameEdit = "name"
const val deadlineEdit = "deadline"
const val descriptionEdit = "description"
const val typeEdit = "type"
const val statusEdit = "status"

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerlayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navview: NavigationView = findViewById (R.id.nav_view)

        toggle = ActionBarDrawerToggle( this,drawerlayout,R.string.open,R.string.close)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navview.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miPersonal -> {
                    val skillsFragment = fragmenttasks.newInstance(
                        "Personal"
                    )
                    changeFragment(skillsFragment,"")
                    drawerlayout.closeDrawer(GravityCompat.START)
                }
                R.id.miWork -> {
                    val skillsFragment = fragmenttasks.newInstance(
                        "Work"
                    )
                    changeFragment(skillsFragment,"")
                    drawerlayout.closeDrawer(GravityCompat.START)
                }
                R.id.miSchool -> {
                    val skillsFragment = fragmenttasks.newInstance(
                        "School"
                    )
                    changeFragment(skillsFragment,"")
                    drawerlayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener {
            drawerlayout.openDrawer(GravityCompat.START)
        }

        supportActionBar?.setDisplayShowHomeEnabled(true)
        val skillsFragment = fragmenttasks.newInstance(
           "Personal"
           )
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, skillsFragment).commit()

        bottomNav = findViewById(R.id.bottomNavigationView)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mitask -> {
                    changeFragment(skillsFragment,"")
                    toolbar.setTitle("Tasks");
                }
                R.id.micalendar -> {
                    changeFragment(CalenderFragment(),"")
                    toolbar.setTitle("Calendar");
                }
                R.id.miGraph -> {
                    changeFragment(graphFragment(),"")
                    toolbar.setTitle("Graph");
                }

            }
            true
        }

    }

    private fun changeFragment(fragment: Fragment, name: String) {

        if (name.isEmpty())
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        else
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("").commit()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.addmenu ->{


                    val mainIntent = Intent(this, addTask::class.java)


                    startActivity(mainIntent)
                    finish()

            }
        }

        return super.onOptionsItemSelected(item)
    }
}