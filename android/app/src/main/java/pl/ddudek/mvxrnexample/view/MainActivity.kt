package pl.ddudek.mvxrnexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import pl.ddudek.mvxrnexample.expenselist.GetExpenseListUseCase
import pl.ddudek.mvxrnexample.view.example.ExampleView
import pl.ddudek.mvxrnexample.view.example.ExampleViewAndroidImpl
import pl.ddudek.mvxrnexample.view.example.ExampleViewReactNativeImpl

class MainActivity : AppCompatActivity() {

    lateinit var view: ExampleView
    private val useCase = GetExpenseListUseCase()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isReactNative = false
        view = if (isReactNative) {
            ExampleViewReactNativeImpl(this, null)
        } else {
            ExampleViewAndroidImpl(layoutInflater, null)
        }

        setContentView(view.getRootView())
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {

        view.onCreated(ExampleView.ViewState("", "",  error = null, loading = true))

        var disposable = useCase.run()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    var expense = data[0]
                    view.applyViewState(ExampleView.ViewState(expense.merchant, expense.user.first, error = null, loading = false))
                }, { error ->
                    view.applyViewState(ExampleView.ViewState("", "", error = "Loading data failed, please try again. \n Details: ${error.message}", loading = false))
                })

        compositeDisposable.add(disposable)

        super.onPostCreate(savedInstanceState)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        view.destroy()
        super.onDestroy()
    }
}
