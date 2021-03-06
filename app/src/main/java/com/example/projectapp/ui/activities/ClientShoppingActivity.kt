package com.example.projectapp.ui.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem

import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.projectapp.R
import com.example.projectapp.firestore.FirestoreClass
import com.example.projectapp.models.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class ClientShoppingActivity : BaseActivity() {


    private lateinit var toggle: ActionBarDrawerToggle //menu map toggle
    private var userType: Int = 0
    private lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_shopping)
        auth = FirebaseAuth.getInstance()
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view_client_shopping)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle) //listen when toggle is clicked
        toggle.syncState() //sync to state (open or closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().checkIfAdmin(this)


        //what to do on each item in the menu
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.nav_my_orders -> Toast.makeText(this, "My Orders Clicked!", Toast.LENGTH_SHORT)
                    .show()
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
                R.id.nav_home -> {
                    startActivity(Intent(this, ProductListActivity::class.java))
                }
                R.id.nav_my_cart -> {
                    startActivity(Intent(this, CartListActivity::class.java))
                }

                R.id.nav_login -> {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, SignInActivity::class.java))
                }

                R.id.nav_admin -> {
                    Log.e(
                        this.javaClass.simpleName,
                        "User type is $userType",)
                    if (userType == 1) {
                        startActivity(Intent(this, DashboardActivity::class.java))
                    } else {
                        showErrorSnackBar("You don't have access!", true)
                    }
                }
            }
            true
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        doubleBackToExit()
    }


    fun setUserType(type: Int) {
            userType = type
    }



}