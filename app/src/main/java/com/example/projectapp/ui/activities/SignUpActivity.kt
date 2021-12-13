package com.example.projectapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projectapp.R
import com.example.projectapp.firestore.FirestoreClass
import com.example.projectapp.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SignUpActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        var btn: Button = findViewById(R.id.signUp_Button)
        btn.setOnClickListener {
            var firstName: String =
                findViewById<EditText>(R.id.signUp_first_name).text.toString().trim()
            var lastName: String =
                findViewById<EditText>(R.id.signUp_last_name).text.toString().trim()
            var email: String = findViewById<EditText>(R.id.signUp_email).text.toString().trim()
            var password: String = findViewById<EditText>(R.id.signUp_pass).text.toString()
            var confirmedPassword: String =
                findViewById<EditText>(R.id.signUp_pass_confirm).text.toString()
            if (validateForm(firstName, lastName, email, password, confirmedPassword))
                register(firstName, lastName, email, password)
        }
    }

    private fun register(firstName: String, lastName: String, email: String, password: String) {
        //create a user with given credentials
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                //if creation was successful
                if (task.isSuccessful) {
                    Toast.makeText(this, "Done!", Toast.LENGTH_LONG).show()
                    val firebaseUser: FirebaseUser = task.result!!.user!! //firebase user instance
                    val user = User(firebaseUser.uid, firstName, lastName, email)
                    FirestoreClass().registerUser(this, user)
                    startActivity(Intent(this, IntroActivity::class.java))
                }
                //if creation was failed
                else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun validateForm(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Boolean {
        return when {
            TextUtils.isEmpty(firstName) -> {
                showErrorSnackBar("Please enter first name",true)
                false
            }
            TextUtils.isEmpty(lastName) -> {
                showErrorSnackBar("Please enter last name",true)
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email address",true)
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password",true)
                false
            }
            !TextUtils.equals(password, passwordConfirm) -> {
                showErrorSnackBar("Passwords didn't match!",true)
                false
            }
            else -> {
                true
            }
        }
    }
}