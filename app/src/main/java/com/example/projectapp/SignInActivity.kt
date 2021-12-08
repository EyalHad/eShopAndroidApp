package com.example.projectapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projectapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()
        var btn : Button = findViewById(R.id.signIn_Button)
        btn.setOnClickListener {
            signIn()
        }
    }

    public fun signIn() {
        val email: String = findViewById<EditText>(R.id.signIn_Email).text.toString().trim()
        val password: String = findViewById<EditText>(R.id.signIn_Pass).text.toString().trim()
        //if the credentials are legit
        if (validateForm(email, password)) {
            showProgressDialog(R.string.wait.toString())
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        Toast.makeText(
                            baseContext, "Authentication succeed!.",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d(TAG, "signInWithEmail:fail")
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }
        }
    }
}