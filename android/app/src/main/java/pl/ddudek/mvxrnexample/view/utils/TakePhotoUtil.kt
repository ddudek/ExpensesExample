package pl.ddudek.mvxrnexample.view.utils

interface TakePhotoUtil {

    fun takePhoto()

    interface Listener {
        fun onReceiptPhotoReady(fullPath: String)
    }
}