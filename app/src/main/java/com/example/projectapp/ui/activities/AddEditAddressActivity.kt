package com.example.projectapp.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.projectapp.R
import com.example.projectapp.firestore.FirestoreClass
import com.example.projectapp.models.Address
import com.example.projectapp.utils.Constants
import kotlinx.android.synthetic.main.activity_add_edit_address.*
import kotlinx.android.synthetic.main.activity_add_edit_address.tv_title
import kotlinx.android.synthetic.main.activity_sign_in.*

/**
 * Add edit address screen.
 */
class AddEditAddressActivity : BaseActivity() {

    private var addressDetails: Address? = null

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_add_edit_address)

        if (intent.hasExtra(Constants.EXTRA_ADDRESS_DETAILS)) {
            addressDetails =
                intent.getParcelableExtra(Constants.EXTRA_ADDRESS_DETAILS)!!
        }

        setupActionBar()

        if (addressDetails != null) {
            if (addressDetails!!.id.isNotEmpty()) {

                tv_title.text = resources.getString(R.string.title_edit_address)
                btn_submit_address.text = resources.getString(R.string.btn_lbl_update)

                et_full_name.setText(addressDetails?.name)
                et_phone_number.setText(addressDetails?.mobileNumber)
                et_city.setText(addressDetails?.city)
                et_street.setText(addressDetails?.street)
                et_house_number.setText(addressDetails?.houseNumber)
                et_zip_code.setText(addressDetails?.zipCode)
                et_additional_note.setText(addressDetails?.additionalNote)

                when (addressDetails?.type) {
                    Constants.HOME -> {
                        rb_home.isChecked = true
                    }
                    Constants.OFFICE -> {
                        rb_office.isChecked = true
                    }
                    else -> {
                        rb_other.isChecked = true

                    }
                }
            }
        }

        rg_type.setOnCheckedChangeListener { _, checkedId ->

            when(checkedId == R.id.rb_other)
            {
                TextUtils.isEmpty(et_additional_note.text.toString().trim { it <= ' ' }) -> {
                    showErrorSnackBar("You Should Provide Additional Note ", true)
                    false
                }
            }
        }

        btn_submit_address.setOnClickListener {
            saveAddressToFirestore()
        }
    }

    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_add_edit_address_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_white_24)
        }

        toolbar_add_edit_address_activity.setNavigationOnClickListener { onBackPressed() }
    }


    /**
     * A function to validate the address input entries.
     */
    private fun validateData(): Boolean {
        return when {

            TextUtils.isEmpty(et_full_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_please_enter_full_name),
                    true
                )
                false
            }

            TextUtils.isEmpty(et_phone_number.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_please_enter_phone_number),
                    true
                )
                false
            }

            TextUtils.isEmpty(et_city.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("You must provide City in the Address", true)
                false
            }

            TextUtils.isEmpty(et_street.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Street field cannot be left empty", true)
                false
            }

            TextUtils.isEmpty(et_house_number.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("You Should Provide Address Number", true)
                false
            }

            TextUtils.isEmpty(et_zip_code.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_please_enter_zip_code), true)
                false
            }

            rb_other.isChecked && TextUtils.isEmpty(
                et_additional_note.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please Provide Additional Note - You chose Other, ", true)
                false
            }
            else -> {
                true
            }
        }
    }

    /**
     * A function to save the address to the cloud firestore.
     */
    private fun saveAddressToFirestore() {

        // Here we get the text from editText and trim the space
        val fullName: String = et_full_name.text.toString().trim { it <= ' ' }
        val phoneNumber: String = et_phone_number.text.toString().trim { it <= ' ' }
        val city: String = et_city.text.toString().trim { it <= ' ' }
        val street: String = et_street.text.toString().trim { it <= ' ' }
        val houseNumber: String = et_house_number.text.toString().trim { it <= ' ' }
        val zipCode: String = et_zip_code.text.toString().trim { it <= ' ' }
        val additionalNote: String = et_additional_note.text.toString().trim { it <= ' ' }

        if (validateData()) {

            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            val addressType: String = when {
                rb_home.isChecked -> {
                    Constants.HOME
                }
                rb_office.isChecked -> {
                    Constants.OFFICE
                }
                else -> {
                    Constants.OTHER
                }
            }

            val addressModel = Address(
                FirestoreClass().getCurrentUserID(),
                fullName,
                phoneNumber,
                city, street, houseNumber,
                zipCode,
                additionalNote,
                addressType,

            )

            if (addressDetails != null && addressDetails!!.id.isNotEmpty()) {
                FirestoreClass().updateAddress(
                    this@AddEditAddressActivity,
                    addressModel,
                    addressDetails!!.id
                )
            } else {
                FirestoreClass().addAddress(this@AddEditAddressActivity, addressModel)
            }
        }
    }

    /**
     * A function to notify the success result of address saved.
     */
    fun addUpdateAddressSuccess() {

        // Hide progress dialog
        dismissDialog()

        val notifySuccessMessage: String =
            if (addressDetails != null && addressDetails!!.id.isNotEmpty()) {
                resources.getString(R.string.msg_your_address_updated_successfully)
            } else {
                resources.getString(R.string.err_your_address_added_successfully)
            }

        Toast.makeText(
            this@AddEditAddressActivity,
            notifySuccessMessage,
            Toast.LENGTH_SHORT
        ).show()

        setResult(RESULT_OK)
        finish()
    }
}