package com.example.projectapp.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.projectapp.databinding.ActivityBaseBinding
import com.example.projectapp.databinding.ActivityStorageBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class StorageActivity : AppCompatActivity() {

    lateinit var binding: ActivityStorageBinding
    lateinit var imageUri : Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectImageBtn.setOnClickListener {

            selectImage()
        }

        binding.uploadImageBtn.setOnClickListener {

            uploadImage()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent,100)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK){
            imageUri = data?.data!!
            binding.firebaseImage.setImageURI(imageUri)
        }

    }

    private fun uploadImage() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
        storageReference.putFile(imageUri)
            .addOnSuccessListener {
                binding.firebaseImage.setImageURI(null)
                Toast.makeText(this@StorageActivity, "successfully uploaded", Toast.LENGTH_SHORT).show()

                if (progressDialog.isShowing) progressDialog.dismiss()

            } .addOnFailureListener {

                if (progressDialog.isShowing) progressDialog.dismiss()

                Toast.makeText(this@StorageActivity, "Failed :(", Toast.LENGTH_SHORT).show()

            }
    }
}