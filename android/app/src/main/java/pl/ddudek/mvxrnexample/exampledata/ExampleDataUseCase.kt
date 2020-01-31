package pl.ddudek.mvxrnexample.exampledata

import io.reactivex.Single
import java.util.concurrent.TimeUnit

class ExampleDataUseCase {
    fun run() : Single<ExampleData> {
        return Single
                .just(ExampleData("Some title", "Some subtitle"))
                .delay(10, TimeUnit.SECONDS)
    }
}