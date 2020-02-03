package pl.ddudek.mvxrnexample.view.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import pl.ddudek.mvxrnexample.BuildConfig
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class TakePhotoUtilImpl(val activity: Activity) : TakePhotoUtil {

    private val requestCodeTakeImage = 1

    var currentPhotoPath: String? = null

    var listener: TakePhotoUtil.Listener? = null

    override fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val authority = "${BuildConfig.APPLICATION_ID}.fileprovider"
                    val photoUri: Uri = FileProvider.getUriForFile(
                            activity,
                            authority,
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    activity.startActivityForResult(takePictureIntent, requestCodeTakeImage)
                }
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == requestCodeTakeImage && resultCode == AppCompatActivity.RESULT_OK) {
            currentPhotoPath?.let { listener?.onReceiptPhotoReady(it) }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun onSaveInstanceState(outState: Bundle) {
        currentPhotoPath?.let { outState.putString("currentPhotoPath", it)}
    }

    fun recreateFromSavedInstanceState(bundle: Bundle) {
        currentPhotoPath = bundle.getString("currentPhotoPath", null)
    }
}