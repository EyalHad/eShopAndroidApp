package com.example.projectapp.activities.firestore

import com.example.projectapp.activities.ui.activities.SignUpActivity
import com.example.projectapp.activities.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

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