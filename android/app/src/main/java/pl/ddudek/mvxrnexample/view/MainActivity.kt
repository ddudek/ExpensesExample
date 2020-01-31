package pl.ddudek.mvxrnexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import com.facebook.react.ReactFragment
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.ddudek.mvxrnexample.expenselist.GetExpenseListUseCase
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import pl.ddudek.mvxrnexample.view.expenselist.ExpensesListView
import pl.ddudek.mvxrnexample.view.expenselist.ExpensesListViewFragmentImpl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : FragmentActivity(), DefaultHardwareBackBtnHandler {

    lateinit var view: ExpensesListView
    lateinit var useCase: GetExpenseListUseCase

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        useCase = GetExpenseListUseCase(createApi())

//        supportFragmentManager.fragmentFactory = object : FragmentFactory() {
//            override fun instantiate(classLoader: ClassLoader, className: String) =
//                    when (className) {
//                        ReactFragment::class.java.name -> ReactFragment()
//                        else -> super.instantiate(classLoader, className)
//                    }
//        }

        view = ExpensesListViewFragmentImpl(this, layoutInflater)
        setContentView(view.getRootView())
    }

    private fun createApi(): ExpensesApi {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val api: ExpensesApi = retrofit.create(ExpensesApi::class.java)
        return api
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {

        val initialState = ExpensesListView.ViewState(listOf(), error = null, loading = true)
        view.onCreated(initialState)

        var disposable = useCase.run()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    view.applyViewState(ExpensesListView.ViewState(data, error = null, loading = false))
                }, { error ->
                    view.applyViewState(ExpensesListView.ViewState(listOf(), error = "Loading data failed, please try again. \n Details: ${error.message}", loading = false))
                })
        compositeDisposable.add(disposable)

        super.onPostCreate(savedInstanceState)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        view.destroy()
        super.onDestroy()
    }

    override fun invokeDefaultOnBackPressed() {
        onBackPressed()
    }
}
