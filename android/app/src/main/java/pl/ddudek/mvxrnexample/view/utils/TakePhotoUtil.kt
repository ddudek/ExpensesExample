package pl.ddudek.mvxrnexample.view.utils

import android.os.Bundle

interface TakePhotoUtil {

    var listener: Listener?

    fun takePhoto()

    fun onSaveInstanceState(outState: Bundle)
    fun recreateFromSavedInstanceState(it: Bundle): Any
    fun onActivityResult(requestCode: Int, resultCode: Int)

    interface Listener {
        fun onPhotoReady(fullPath: String)
    }
}