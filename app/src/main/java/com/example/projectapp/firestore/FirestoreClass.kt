package com.example.projectapp.firestore

import android.app.Activity
import android.net.Uri
import android.util.Log
import com.example.projectapp.ui.activities.AddProductActivity
import com.example.projectapp.ui.activities.SignUpActivity
import com.example.projectapp.models.Product
import com.example.projectapp.models.User
import com.example.projectapp.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreClass {
    
    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, user: User){
        //create or add to the same collection as given in FireStore
        mFireStore.collection("users")
            //Document by unique ID of each user
            .document(user.getId()).set(user, SetOptions.merge())
            .addOnSuccessListener {

            }
    }

}