package com.example.projectapp.models



import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A data model class for Address item with required fields.
 */
@Parcelize
data class Address(
    val user_id: String = "",
    val name: String = "",
    val mobileNumber: String = "",

    val city: String = "",
    val street: String = "",
    val houseNumber: String = "",
    val zipCode: String = "",
    val additionalNote: String = "",

    val type: String = "",
    var id: String = "",

) : Parcelable