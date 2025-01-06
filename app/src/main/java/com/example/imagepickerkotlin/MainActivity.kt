package com.example.imagepickerkotlin

import android.content.*
import android.graphics.*
import android.os.*
import android.provider.*
import android.util.*
import android.widget.*
import androidx.activity.result.contract.*
import androidx.appcompat.app.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val buttonPick = findViewById<Button>(R.id.button)
        val buttonCamera = findViewById<Button>(R.id.buttonCamera)

        // Register the launcher for gallery
        val launcherGallery =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK && result.data?.data != null) {

                    Log.d("MainActivity", "Image URI: ${result.data?.data}")

                    val imageUri = result.data?.data
                    try {
                        val bitmap: Bitmap =
                            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                        imageView.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        // Register the launcher for taking a picture and saving to a URI
        val launcherCamera =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val image = result.data!!.extras!!.get("data") as Bitmap
                    imageView.setImageBitmap(image) // Display the captured image from URI
                } else {
                    Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
                }
            }

        buttonPick.setOnClickListener {
            // Open gallery
            val openGallery = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*" // Correct type
            }
            launcherGallery.launch(openGallery)
        }

        buttonCamera.setOnClickListener {
            val openCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcherCamera.launch(openCamera);
        }
    }
}
