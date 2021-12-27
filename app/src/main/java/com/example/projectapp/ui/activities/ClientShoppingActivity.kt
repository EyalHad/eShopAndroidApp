package com.example.projectapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.projectapp.R
import com.example.projectapp.firestore.FirestoreClass
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class ClientShoppingActivity : BaseActivity() {

    private lateinit var toggle : ActionBarDrawerToggle //menu map toggle

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
                R.id.nav_home -> Toast.makeText(this, "Home Clicked!", Toast.LENGTH_SHORT).show()
                R.id.nav_my_orders -> Toast.makeText(this, "My Orders Clicked!", Toast.LENGTH_SHORT).show()
                R.id.nav_settings -> {

                    Toast.makeText(this, "Settings Clicked!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,SettingsActivity::class.java))
                }

                R.id.nav_login ->{
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, SignInActivity::class.java))
                }

                R.id.nav_admin -> startActivity(Intent(this, DashboardActivity::class.java))

//                {
//                    start
//                    val type : Int = FirestoreClass().checkIfAdmin()
//                    if(type.equals(0)){
//                        Toast.makeText(this, "Not an Admin!", Toast.LENGTH_SHORT).show()
//                    }
//                }

            }
            true
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){return true}
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        doubleBackToExit()
    }




}