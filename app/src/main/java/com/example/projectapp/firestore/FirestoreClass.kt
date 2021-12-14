package com.example.projectapp.firestore

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import com.example.projectapp.ui.activities.AddProductActivity
import com.example.projectapp.ui.activities.SignUpActivity
import com.example.projectapp.models.Product
import com.example.projectapp.models.User
import com.example.projectapp.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference



class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, user: User) {
        //create or add to the same collection as given in FireStore
        mFireStore.collection("users")
            //Document by unique ID of each user
            .document(user.getId()).set(user, SetOptions.merge())
            .addOnSuccessListener {

            }
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileUri: Uri?, imageType: String) {


        // Getting the Storage Reference
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(
                activity,
                imageFileUri
            )
        )

        // Adding the file to Reference

        sRef.putFile(imageFileUri!!)
            .addOnSuccessListener { taskSnapshot ->
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                // Get the downloadable URL from taskSnapshot

                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.e("Downloadable Image URL", uri.toString())

                        when (activity) {

                            is AddProductActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }
                        }
                    }
            }
            .addOnFailureListener {
                // Hide the progress dialog if there is any error. Print error in LOG
                    exception ->

                when (activity) {

                    is AddProductActivity -> {
//                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    fun uploadProductDetails(activity: AddProductActivity, productInfo: Product) {



        mFireStore.collection(Constants.PRODUCTS)
            .document()
            .set(productInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.productUploadSuccess()
            }
            .addOnFailureListener { e ->

//                activity.hideProgressDialog()

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the details",
                    e
                )
            }
    }
}