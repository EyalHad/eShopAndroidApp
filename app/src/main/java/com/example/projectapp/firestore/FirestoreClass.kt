package com.example.projectapp.firestore

import android.app.Activity
import android.net.Uri
import android.util.Log
import com.example.projectapp.models.Address
import com.example.projectapp.models.Product
import com.example.projectapp.models.User
import com.example.projectapp.ui.activities.*
import com.example.projectapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreClass {

    private val fireStore = FirebaseFirestore.getInstance()
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDatabase : DatabaseReference
    private lateinit var user : com.google.firebase.firestore.auth.User

    /** ------------------- Authentication Area ----------------------------------------------------------------------------*/
    /** -------------------                     -------------------*/
    fun checkIfAdmin(activity: ClientShoppingActivity){
        getUserDetails(activity)
        activity.dismissDialog()
    }

    /**
     * A function to get the user id of current logged user.
     */
    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    fun registerUser(activity: Activity, user: User) {

        when (activity) {

            is SignUpActivity -> {
                //create or add to the same collection as given in FireStore
                fireStore.collection("Users")
                    //Document by unique ID of each user
                    .document(user.id).set(user, SetOptions.merge())
                    .addOnSuccessListener {

                        // Here call a function of base activity for transferring the result to it.
                        activity.userRegistrationSuccess()
                    }
                    .addOnFailureListener { e ->
                        activity.dismissDialog()
                        Log.e(
                            activity.javaClass.simpleName,
                            "Error while registering the user.",
                            e
                        )
                    }
            }

            is AdminRegistrationActivity -> {
                //create or add to the same collection as given in FireStore
                fireStore.collection("Users")
                    //Document by unique ID of each user
                    .document(user.id).set(user, SetOptions.merge())
                    .addOnSuccessListener {

                        // Here call a function of base activity for transferring the result to it.
                        activity.userRegistrationSuccess()
                    }
                    .addOnFailureListener { e ->
                        activity.dismissDialog()
                        Log.e(
                            activity.javaClass.simpleName,
                            "Error while registering the Admin.",
                            e
                        )
                    }
            }
        }

    }


    /** -------------------  Image Upload  ----------------------------------------------------------------------------*/


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

                            is UserProfileActivity -> {
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

    /** -------------------  Product Details ----------------------------------------------------------------------------*/

    fun uploadProductDetails(activity: AddProductActivity, productInfo: Product) {
        fireStore.collection(Constants.PRODUCTS)
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



    /** -------------------  User Details ----------------------------------------------------------------------------*/


    /**
     * A function to update the user profile data into the database.
     *
     * @param activity The activity is used for identifying the Base activity to which the result is passed.
     * @param userHashMap HashMap of fields which are to be updated.
     */
    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        // Collection Name
        fireStore.collection(Constants.USERS)
            // Document ID against which the data to be updated. Here the document id is the current logged in user id.
            .document(getCurrentUserID())
            // A HashMap of fields which are to be updated.
            .update(userHashMap)
            .addOnSuccessListener {

                // Notify the success result.
                when (activity) {
                    is UserProfileActivity -> {
                        // Call a function of base activity for transferring the result to it.
                        activity.userProfileUpdateSuccess()
                    }
                }
            }
            .addOnFailureListener { e ->

                when (activity) {
                    is UserProfileActivity -> {
                        // Hide the progress dialog if there is any error. And print the error in log.
                        activity.dismissDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while updating the user details.",
                    e
                )
            }
    }

    /**
     * A function to get the logged user details from from FireStore Database.
     */
    fun getUserDetails(activity: Activity) {

        // Here we pass the collection name from which we wants the data.
        fireStore.collection(Constants.USERS)
            // The document id to get the Fields of user.
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())

                // Here we have received the document snapshot which is converted into the User Data model object.
                val user = document.toObject(User::class.java)!!


                when (activity) {
                    is SignInActivity -> {
                        // Call a function of base activity for transferring the result to it.
                        activity.userLoggedInSuccess(user)
                    }

                    is SettingsActivity -> {
                        // Call a function of base activity for transferring the result to it.
                        activity.userDetailsSuccess(user)
                    }

                    is ClientShoppingActivity -> {
                        activity.setUserType(user.type)

                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error. And print the error in log.
                when (activity) {
                    is SignInActivity -> {
                        activity.dismissDialog()
                    }
                    is SettingsActivity -> {
                        activity.dismissDialog()
                    }
                    is ClientShoppingActivity -> {
                        activity.dismissDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details.",
                    e
                )
            }
    }



    /** -------------------  Address Details ----------------------------------------------------------------------------*/

    /**
     * A function to add address to the cloud firestore.
     *
     * @param activity
     * @param addressInfo
     */
    fun addAddress(activity: AddEditAddressActivity, addressInfo: Address) {

        // Collection name address.
        fireStore.collection(Constants.ADDRESSES)
            .document()
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(addressInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.addUpdateAddressSuccess()
            }
            .addOnFailureListener { e ->
                activity.dismissDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while adding the address.",
                    e
                )
            }
    }

    /**
     * A function to update the existing address to the cloud firestore.
     *
     * @param activity Base class
     * @param addressInfo Which fields are to be updated.
     * @param addressId existing address id
     */
    fun updateAddress(activity: AddEditAddressActivity, addressInfo: Address, addressId: String) {

        fireStore.collection(Constants.ADDRESSES)
            .document(addressId)
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(addressInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.addUpdateAddressSuccess()
            }
            .addOnFailureListener { e ->
                activity.dismissDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while updating the Address.",
                    e
                )
            }
    }

    /**
     * A function to delete the existing address from the cloud firestore.
     *
     * @param activity Base class
     * @param addressId existing address id
     */
    fun deleteAddress(activity: AddressListActivity, addressId: String) {

        fireStore.collection(Constants.ADDRESSES)
            .document(addressId)
            .delete()
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.deleteAddressSuccess()
            }
            .addOnFailureListener { e ->
                activity.dismissDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while deleting the address.",
                    e
                )
            }
    }

    /**
     * A function to get the list of address from the cloud firestore.
     *
     * @param activity
     */
    fun getAddressesList(activity: AddressListActivity) {
        // The collection name for PRODUCTS
        fireStore.collection(Constants.ADDRESSES)
            .whereEqualTo(Constants.USER_ID, getCurrentUserID())
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // Here we get the list of boards in the form of documents.
                Log.e(activity.javaClass.simpleName, document.documents.toString())
                // Here we have created a new instance for address ArrayList.
                val addressList: ArrayList<Address> = ArrayList()

                // A for loop as per the list of documents to convert them into Boards ArrayList.
                for (i in document.documents) {

                    val address = i.toObject(Address::class.java)!!
                    address.id = i.id

                    addressList.add(address)
                }

                activity.successAddressListFromFirestore(addressList)
            }
            .addOnFailureListener { e ->
                // Here call a function of base activity for transferring the result to it.

                activity.dismissDialog()

                Log.e(activity.javaClass.simpleName, "Error while getting the address list.", e)
            }
    }
    /**
     * A function to get the list of products from the cloud firestore.
     *
     * @param activity
     */

    fun getProductsList(activity:ProductListActivity) {
        // The collection name for PRODUCTS
        fireStore.collection("Products")

            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // Here we get the list of boards in the form of documents.
                Log.e(activity.javaClass.simpleName, document.documents.toString())
                // Here we have created a new instance for address ArrayList.
                val addressList: ArrayList<Product> = ArrayList()

                // A for loop as per the list of documents to convert them into Boards ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)!!


                    addressList.add(product)
                }

                activity.successAddressListFromFirestore(addressList  )
            }
            .addOnFailureListener { e ->
                // Here call a function of base activity for transferring the result to it.

                activity.dismissDialog()

                Log.e(activity.javaClass.simpleName, "Error while getting the address list.", e)
            }
    }
//    fun deleteProduct(activity: ProductListActivity, addressId: String) {
//
//        fireStore.collection(Constants.ADDRESSES)
//            .document(addressId)
//            .delete()
//            .addOnSuccessListener {
//
//                // Here call a function of base activity for transferring the result to it.
//                activity.deleteAddressSuccess()
//            }
//            .addOnFailureListener { e ->
//                activity.dismissDialog()
//                Log.e(
//                    activity.javaClass.simpleName,
//                    "Error while deleting the address.",
//                    e
//                )
//            }
//    }



    fun getCartList(activity:CartListActivity,email: String) {
        // The collection name for PRODUCTS
        fireStore.collection(email)

            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                // Here we get the list of boards in the form of documents.
                Log.e(activity.javaClass.simpleName, document.documents.toString())
                // Here we have created a new instance for address ArrayList.
                val addressList: ArrayList<Product> = ArrayList()

                // A for loop as per the list of documents to convert them into Boards ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)!!


                    addressList.add(product)
                }

                activity.successAddressListFromFirestore(addressList  )
            }
            .addOnFailureListener { e ->
                // Here call a function of base activity for transferring the result to it.

                activity.dismissDialog()

                Log.e(activity.javaClass.simpleName, "Error while getting the address list.", e)
            }
    }
//    fun deleteProduct(activity: CartListActivity, addressId: String) {
//
//        fireStore.collection(Constants.ADDRESSES)
//            .document(addressId)
//            .delete()
//            .addOnSuccessListener {
//
//                // Here call a function of base activity for transferring the result to it.
//                activity.deleteAddressSuccess()
//            }
//            .addOnFailureListener { e ->
//                activity.dismissDialog()
//                Log.e(
//                    activity.javaClass.simpleName,
//                    "Error while deleting the address.",
//                    e
//                )
//            }
//    }

}