# Image Picker Android, Camera Android

https://www.youtube.com/watch?v=-6UdOJ0h5RI
https://www.youtube.com/watch?v=SWXCINZxpqQ

## Add permissions on **AndroidManifest**

```xml
    <uses-permission
        android:name="android.permission.WRITE_EXTERNAL_STORAGE"
        android:maxSdkVersion="29" />
    <uses-permission
        android:name="android.permission.READ_EXTERNAL_STORAGE"
        android:maxSdkVersion="32" />
    <uses-permission
        android:name="android.permission.READ_MEDIA_IMAGES"
        android:minSdkVersion="33" />
```

## Make UI to on **activity_main.xml**

```xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="250dp"
        android:layout_height="250dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        android:layout_marginTop="50dp"
        />

    <Button
        android:id="@+id/button"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_marginTop="50dp"
        android:text="upload"
        app:layout_constraintTop_toBottomOf="@id/imageView"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
       />

    <Button
        android:id="@+id/buttonCamera"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_marginTop="50dp"
        android:text="Camera"
        app:layout_constraintTop_toBottomOf="@id/button"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        />


</androidx.constraintlayout.widget.ConstraintLayout>

```

## Add functionality for handling **MainActivity.kt**

```kt

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


```

## Demo run app

![Alt text](image/imagePicker.png)
