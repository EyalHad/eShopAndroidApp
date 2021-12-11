package com.example.projectapp.ui.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.projectapp.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.jar.Manifest

class StorageActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        var btn_select: Button = findViewById(R.id.btn_select_image)
        btn_select.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {

                // Select Image
                val gallertIntent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(gallertIntent, 222)
            } else {
                // Request Permission

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 121
                )
            }

        }

        var btn_upload: Button = findViewById(R.id.btn_upload_image)
        btn_upload.setOnClickListener {

            if (imageUri != null) {
                val imageExtention = MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(contentResolver.getType(imageUri!!))

                val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                    "Image" + System.currentTimeMillis() + "." + imageExtention
                )
                var text: TextView = findViewById(R.id.success)
                sRef.putFile(imageUri!!)
                    .addOnSuccessListener { taskSnapshot ->
                        taskSnapshot.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { url ->
                                text.text = "Image Upload Successfully:\n $url"
                            }.addOnFailureListener { ex ->
                                Toast.makeText(
                                    this,
                                    ex.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.e(javaClass.simpleName, ex.message, ex)
                            }
                    }
            } else {
                Toast.makeText(
                    this,
                    "Please Choose image to Upload",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 121) {
            val gallertIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(gallertIntent, 222)
        } else {
            Toast.makeText(
                this,
                "oops, you just denied the permission for storage",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 222) {
                if (data != null) {
                    try {

                        imageUri = data.data!!
                        var image_view: ImageView = findViewById(R.id.image_view)
                        image_view.setImageURI(imageUri)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@StorageActivity,
                            "Image Selection Failed !!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}