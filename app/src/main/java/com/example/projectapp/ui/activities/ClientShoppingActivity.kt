package com.example.projectapp.ui.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.projectapp.R
import com.example.projectapp.firestore.FirestoreClass
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class ClientShoppingActivity : BaseActivity() {

    private lateinit var toggle : ActionBarDrawerToggle //menu map toggle
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_shopping)
        auth = FirebaseAuth.getInstance()
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
                R.id.nav_settings -> Toast.makeText(this, "Settings Clicked!", Toast.LENGTH_SHORT).show()
                R.id.nav_edit_profile -> Toast.makeText(this, "Edit Profile Clicked!", Toast.LENGTH_SHORT).show()
                R.id.nav_login -> startActivity(Intent(this, SignInActivity::class.java))
                R.id.nav_admin -> {
                    admin()
                }

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

    private fun admin() {
        val email: String = findViewById<EditText>(R.id.signIn_Email).text.toString().trim() //email field
        val password: String = findViewById<EditText>(R.id.signIn_Pass).text.toString().trim() //password field
        //if the credentials are legit
        if (validateForm(email, password)) {
            showProgressDialog(R.string.wait.toString())
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "Admin:success")
                        Toast.makeText(
                            baseContext, "Authentication succeed!.",
                            Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this, DashboardActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d(ContentValues.TAG, "signInWithEmail:fail")
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }
        }
    }

    private fun validateForm( email:String, password:String ): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email address",true)
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password",true)
                false
            }
            else -> {
                true
            }
        }
    }


}