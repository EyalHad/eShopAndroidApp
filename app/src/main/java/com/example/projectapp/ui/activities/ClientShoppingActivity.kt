package com.example.projectapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.projectapp.R
import com.google.android.material.navigation.NavigationView

class ClientShoppingActivity : AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle //menu map toggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_shopping)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view_client_shopping)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle) //listen when toggle is clicked
        toggle.syncState() //sync to state (open or closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //what to do on each item in the menu
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> Toast.makeText(this, "click!", Toast.LENGTH_SHORT).show()
            }
            true
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){return true}
        return super.onOptionsItemSelected(item)
    }


}