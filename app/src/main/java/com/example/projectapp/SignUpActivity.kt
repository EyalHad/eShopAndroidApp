package com.example.projectapp
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.example.projectapp.R


class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        var btn : Button = findViewById(R.id.signUp_Button)
        btn.setOnClickListener {
            register()
        }
    }
        private fun register(){
//        val name:String = findViewById(R.id.signUp_Button).text.toString().trim{it <= ' '}//trim is used to 'clean' empty characters
//        val email:String = signUp_Email.text.toString().trim{it <= ' '}
//        val password:String = signUp_Pass.toString()
//        if(validateForm(name, password, email)){
//            showErrorSnackBar(resources.getString(R.string.wait))
//            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener { task ->
//                    dismissDialog()
//                    if (task.isSuccessful) {
//                        val firebaseUser: FirebaseUser = task.result!!.user!!
//                        val registeredEmail = firebaseUser.email!!
//                        Toast.makeText(
//                            this, "$name you have successfully created with " +
//                                    "email $registeredEmail!", Toast.LENGTH_LONG
//                        ).show()
//                        FirebaseAuth.getInstance().signOut()
//                        finish()
//                    } else {
//                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
//                    }
//                }
//        }
    }

    private fun validateForm(name:String, password:String, email:String ) : Boolean{
        return when{
            TextUtils.isEmpty(name)->{
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please enter a password")
                false
            }
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please enter an email address")
                false
            }
            else ->{
                true
            }
        }
    }
}