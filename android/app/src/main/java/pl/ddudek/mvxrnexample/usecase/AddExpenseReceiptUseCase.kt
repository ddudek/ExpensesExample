package pl.ddudek.mvxrnexample.usecase

import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.ddudek.mvxrnexample.data.cache.ExpensesMemoryCache
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import java.io.File
import java.util.concurrent.TimeUnit

class AddExpenseReceiptUseCase(val api: ExpensesApi, val cache: ExpensesMemoryCache) {
    fun run(expenseId: String, photoFile: File) : Single<Expense> {

        val uploadFormField = "receipt"
        val uploadFileName = "receipt.jpg"
        val mimeType = "image/jpeg"
        val multipartBody = MultipartBody.Part.createFormData(
                uploadFormField,
                uploadFileName, RequestBody
                .create(MediaType.parse(mimeType), photoFile))

        return api.addReceipt(expenseId, multipartBody)
                .doOnSuccess { cache.invalidate() }
                .delay(1, TimeUnit.SECONDS)
    }
}