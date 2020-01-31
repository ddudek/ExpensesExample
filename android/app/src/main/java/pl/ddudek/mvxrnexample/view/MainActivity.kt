package pl.ddudek.mvxrnexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import pl.ddudek.mvxrnexample.exampledata.ExampleDataUseCase

class MainActivity : AppCompatActivity() {

    lateinit var view: ExampleView
    private val useCase = ExampleDataUseCase()

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
                    view.applyViewState(ExampleView.ViewState(data.title, data.subtitle, error = null, loading = false))
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
