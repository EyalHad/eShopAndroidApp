package com.example.projectapp.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.projectapp.R
import com.example.projectapp.firestore.FirestoreClass
import com.example.projectapp.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_add_admin.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class AddAdminActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_admin)
        admin_create_btn.setOnClickListener{
            registerUser()
        }
    }

    /**
     * A function to register the user with email and password using FirebaseAuth.
     */
    private fun registerUser() {

        // Check with validate function if the entries are valid or not.
        if (validateRegisterDetails()) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }

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
                                first_name.text.toString().trim { it <= ' ' },
                                et_last_name.text.toString().trim { it <= ' ' },
                                et_email.text.toString().trim { it <= ' ' }
                            )

                            // Pass the required values in the constructor.
                            FirestoreClass().registerUser(this, user)
                            userRegistrationSuccess()
                            dismissDialog()
                        } else {

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
        Toast.makeText(
            this,
            "User successfully created!",
            Toast.LENGTH_SHORT
        ).show()
    }

        /**
         * A function to validate the entries of a new user.
         */
        private fun validateRegisterDetails(): Boolean {
            return when {
                TextUtils.isEmpty(admin_first_name.text.toString().trim { it <= ' ' }) -> {
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

                admin_et_password.text.toString()
                    .trim { it <= ' ' } != admin_et_confirm_password.text.toString()
                    .trim { it <= ' ' } -> {
                    showErrorSnackBar(
                        resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                        true
                    )
                    false
                }
                !cb_terms_and_condition.isChecked -> {
                    showErrorSnackBar(
                        resources.getString(R.string.err_msg_agree_terms_and_condition),
                        true
                    )
                    false
                }
                else -> {
                    true
                }
            }
        }
}