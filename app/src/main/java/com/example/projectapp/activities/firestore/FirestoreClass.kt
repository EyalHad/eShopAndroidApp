package com.example.projectapp.activities.firestore

import com.example.projectapp.activities.AddProductActivity
import com.example.projectapp.activities.SignUpActivity
import com.example.projectapp.activities.models.Product
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
    fun AddProduct(activity: AddProductActivity, product: Product){
        //create or add to the same collection as given in FireStore
        mFireStore.collection("products")
            //Document by unique ID of each user
            .document(product.getId()).set(product, SetOptions.merge())
            .addOnSuccessListener {

            }
    }
}