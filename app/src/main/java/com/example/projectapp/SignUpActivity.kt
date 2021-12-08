package com.example.projectapp
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth



class SignUpActivity : BaseActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        var btn : Button = findViewById(R.id.signUp_Button)
        btn.setOnClickListener {
            register()
        }
    }
        private fun register(){
            var password:String = findViewById<EditText>(R.id.signUp_Pass).text.toString()
            var email:String = findViewById<EditText>(R.id.signUp_Email).text.toString()
            //create a user with given credentials
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{task: Task<AuthResult> ->
                    //if creation was successful
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Done!", Toast.LENGTH_LONG).show()
                    }
                    //if creation was failed
                    else{
                        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                    }
                }
    }
}