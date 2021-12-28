package com.example.projectapp.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.projectapp.R
import com.example.projectapp.firestore.FirestoreClass
import com.example.projectapp.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_admin.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.btn_register
import kotlinx.android.synthetic.main.activity_sign_up.toolbar_register_activity

class AdminRegistrationActivity : BaseActivity() {
    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_add_admin)

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        // It is deprecated in the API level 30. I will update you with the alternate solution soon.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        add_admin_btn.setOnClickListener {
            registerUser()
        }
    }

    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

//        setSupportActionBar(toolbar_register_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_arrow_back_ios_24)
        }

        toolbar_register_activity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to validate the entries of a new user.
     */
    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(admin_et_first_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(admin_et_last_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(admin_et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(admin_et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(admin_et_confirm_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }

            admin_et_password.text.toString().trim { it <= ' ' } != admin_et_confirm_password.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

    /**
     * A function to register the user with email and password using FirebaseAuth.
     */
    private fun registerUser() {

        // Check with validate function if the entries are valid or not.
        if (validateRegisterDetails()) {

            // Show the progress dialog.
            showProgressDialog(
                resources.getString(R.string.please_wait))

            val email: String = admin_et_email.text.toString().trim { it <= ' ' }
            val password: String = admin_et_password.text.toString().trim { it <= ' ' }

            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            // Instance of User data model class.
                            val user = User(
                                firebaseUser.uid,
                                admin_et_first_name.text.toString().trim { it <= ' ' },
                                admin_et_last_name.text.toString().trim { it <= ' ' },
                                admin_et_email.text.toString().trim { it <= ' ' },
                                "",0,"",0,1
                            )

                            // Pass the required values in the constructor.
                            FirestoreClass().registerUser(this, user)
                            dismissDialog()
                        } else {
                            Toast.makeText(this,"Something went wrong:(", Toast.LENGTH_SHORT).show()
                            // Hide the progress dialog
                            dismissDialog()

                            // If the registering is not successful then show error message.
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    })
        }
    }

    /**
     * A function to notify the success result of Firestore entry when the user is registered successfully.
     */
    fun userRegistrationSuccess() {

        // Hide the progress dialog
        val firstName = admin_et_first_name.text.toString().trim { it <= ' ' }
        val lastName = admin_et_last_name.text.toString().trim { it <= ' ' }

        Toast.makeText(
            this@AdminRegistrationActivity,
            "$firstName $lastName is signed up successfully",
            Toast.LENGTH_SHORT
        ).show()
//
//
//        /**
//         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
//         * and send him to Intro Screen for Sign-In
//         */
//        FirebaseAuth.getInstance().signOut()
//        // Finish the Register Screen
        finish()
    }

    private fun CreateCart(email: String) {
        val db = FirebaseFirestore.getInstance()
        val add = HashMap<String, Any>()
        add["firstName"] = "your first name"
        db.collection(email)
            .add(add)
            .addOnSuccessListener {
                Toast.makeText(this, "Data added ", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, " Data not added ", Toast.LENGTH_LONG).show()
            }

    }
}