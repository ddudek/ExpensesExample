package pl.ddudek.mvxrnexample.usecase

import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import java.util.concurrent.TimeUnit

class AddExpenseReceiptUseCase(val api: ExpensesApi) {
    fun run(expenseId: String, photoPath: String) : Single<Expense> {

        val photo = MultipartBody.Part.createFormData("receipt", "receipt.png", RequestBody
                .create(MediaType.parse("image/png"), photoPath))

        return api.addReceipt(expenseId, photo)
                .delay(1, TimeUnit.SECONDS)
    }
}