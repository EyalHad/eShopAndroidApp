package com.example.projectapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.example.projectapp.R
import com.example.projectapp.firestore.FirestoreClass
import com.example.projectapp.models.User
import com.example.projectapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*


@Suppress("DEPRECATION")
class SignInActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_sign_in)


        // This is used to hide the status bar and make the login screen as a full screen activity.
        // It is deprecated in the API level 30. I will update you with the alternate solution soon.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Click event assigned to Forgot Password text.
        tv_forgot_password.setOnClickListener(this)
        // Click event assigned to Login button.
        btn_login.setOnClickListener(this)
        // Click event assigned to Register text.
        tv_register.setOnClickListener(this)



    }


    /**
     * A function to validate the login entries of a user.
     */
    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(admin_et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(admin_et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }

    /**
     * A function to notify user that logged in success and get the user details from the FireStore database after authentication.
     */
    fun userLoggedInSuccess(user: User) {

        // Hide the progress dialog.
        dismissDialog()

        if (user.profileCompleted == 0) {
            // If the user profile is incomplete then launch the UserProfileActivity.
            val intent = Intent(this@SignInActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        } else {
            // Redirect the user to Dashboard Screen after log in.
            startActivity(Intent(this@SignInActivity, ClientShoppingActivity::class.java))
        }
        finish()
    }

    /**
     * A function to Log-In. The user will be able to log in using the registered email and password with Firebase Authentication.
     */
    private fun logInRegisteredUser() {

        if (validateLoginDetails()) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Get the text from editText and trim the space
            val email = admin_et_email.text.toString().trim { it <= ' ' }
            val password = admin_et_password.text.toString().trim { it <= ' ' }

            // Log-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        FirestoreClass().getUserDetails(this@SignInActivity)
                    } else {
                        // Hide the progress dialog
                        dismissDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    /**
     * In Login screen the clickable components are Login Button, ForgotPassword text and Register Text.
     */
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.tv_forgot_password -> {

                    // Launch the forgot password screen when the user clicks on the forgot password text.
                    val intent = Intent(this@SignInActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_login -> {

                    logInRegisteredUser()
                }

                R.id.tv_register -> {
                    // Launch the register screen when the user clicks on the text.
                    val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}