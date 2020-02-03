package pl.ddudek.mvxrnexample.usecase

import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import pl.ddudek.mvxrnexample.data.cache.ExpensesMemoryCache
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.networking.ExpensesApi
import pl.ddudek.mvxrnexample.networking.schema.responses.ExpenseListSchema
import pl.ddudek.mvxrnexample.networking.schema.responses.ExpenseSchema
import java.util.*


class GetFilteredExpenseListUseCaseTest {

    @Rule
    @JvmField
    var rule = MockitoJUnit.rule()

    @Mock
    lateinit var expensesApi: ExpensesApi

    @Mock
    lateinit var cache: ExpensesMemoryCache

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test no filtering empty`() {
        // given
        val expenses = listOf<ExpenseSchema>()

        // when
        `when`(expensesApi.getExpensesList()).thenReturn(
                Single.just(ExpenseListSchema(expenses)))

        val useCase = GetFilteredExpenseListUseCase(expensesApi, cache)
        val testObserver: TestObserver<List<Expense>> = useCase
                .run(GetFilteredExpenseListUseCase.NO_FILTER, GetFilteredExpenseListUseCase.NO_FILTER)
                .test()

        //then
        testObserver.awaitTerminalEvent()
        testObserver
                .assertNoErrors()
                .assertValue { l: List<Expense> -> l.size == 0 }

    }

    @Test
    fun `test no filtering not empty`() {
        // given
        val expenses = createExpensesList()

        // when
        `when`(expensesApi.getExpensesList()).thenReturn(
                Single.just(ExpenseListSchema(expenses)))

        val useCase = GetFilteredExpenseListUseCase(expensesApi, cache)
        val testObserver: TestObserver<List<Expense>> = useCase
                .run(GetFilteredExpenseListUseCase.NO_FILTER, GetFilteredExpenseListUseCase.NO_FILTER)
                .test()


        //then
        testObserver.awaitTerminalEvent()
        testObserver
                .assertNoErrors()
                .assertValue { l: List<Expense> -> l.size == 3 }

    }

    @Test
    fun `test min filtering`() {
        // given
        val expenses = createExpensesList()

        // when
        `when`(expensesApi.getExpensesList()).thenReturn(
                Single.just(ExpenseListSchema(expenses)))

        val useCase = GetFilteredExpenseListUseCase(expensesApi, cache)
        val testObserver: TestObserver<List<Expense>> = useCase
                .run(2000, GetFilteredExpenseListUseCase.NO_FILTER)
                .test()


        //then
        testObserver.awaitTerminalEvent()
        testObserver
                .assertNoErrors()
                .assertValue { l: List<Expense> -> l.size == 2 }

    }

    @Test
    fun `test max filtering`() {
        // given
        val expenses = createExpensesList()

        // when
        `when`(expensesApi.getExpensesList()).thenReturn(
                Single.just(ExpenseListSchema(expenses)))

        val useCase = GetFilteredExpenseListUseCase(expensesApi, cache)
        val testObserver: TestObserver<List<Expense>> = useCase
                .run(GetFilteredExpenseListUseCase.NO_FILTER, 2000)
                .test()


        //then
        testObserver.awaitTerminalEvent()
        testObserver
                .assertNoErrors()
                .assertValue { l: List<Expense> -> l.size == 1 && l[0].id == "5b996064dfd5b783915112f5" }

    }

    @Test
    fun `test both filtering`() {
        // given
        val expenses = createExpensesList()

        // when
        `when`(expensesApi.getExpensesList()).thenReturn(
                Single.just(ExpenseListSchema(expenses)))

        val useCase = GetFilteredExpenseListUseCase(expensesApi, cache)
        val testObserver: TestObserver<List<Expense>> = useCase
                .run(2000, 3000)
                .test()


        //then
        testObserver.awaitTerminalEvent()
        testObserver
                .assertNoErrors()
                .assertValue { l: List<Expense> -> l.size == 1 && l[0].id == "5b99606492951fe4481be7c6" }

    }

    @Test
    fun `test no caching`() {
        // given
        val expenses = createExpensesList()

        // when
        `when`(expensesApi.getExpensesList()).thenReturn(
                Single.just(ExpenseListSchema(expenses)))

        val tenMinutes = 10L * 60L * 1000L
        `when`(cache.lastFetchTimestamp).thenReturn(Date().time - tenMinutes)

        val useCase = GetFilteredExpenseListUseCase(expensesApi, cache)
        val testObserver: TestObserver<List<Expense>> = useCase
                .run(GetFilteredExpenseListUseCase.NO_FILTER, GetFilteredExpenseListUseCase.NO_FILTER)
                .test()

        testObserver.awaitTerminalEvent()

        //then
        verify(expensesApi, times(1)).getExpensesList()
        testObserver
                .assertNoErrors()
                .assertValue { l: List<Expense> -> l.size == 3 }

    }

    @Test
    fun `test caching`() {
        // given
        val expenses = createExpensesList()

        // when
        `when`(expensesApi.getExpensesList()).thenReturn(
                Single.just(ExpenseListSchema(expenses)))


        val useCase = GetFilteredExpenseListUseCase(expensesApi, cache)
        val testObserver: TestObserver<List<Expense>> = useCase
                .run(GetFilteredExpenseListUseCase.NO_FILTER, GetFilteredExpenseListUseCase.NO_FILTER)
                .test()

        testObserver.awaitTerminalEvent()


        val oneMinute = 1L * 60L * 1000L
        `when`(cache.lastFetchTimestamp).thenReturn(Date().time - oneMinute)
        `when`(cache.expenses).thenReturn(createCachedExpensesList())

        val testObserver2: TestObserver<List<Expense>> = useCase
                .run(GetFilteredExpenseListUseCase.NO_FILTER, GetFilteredExpenseListUseCase.NO_FILTER)
                .test()

        testObserver2.awaitTerminalEvent()

        //then
        verify(expensesApi, times(1)).getExpensesList()
        testObserver
                .assertNoErrors()
                .assertValue { l: List<Expense> -> l.size == 3  }

        testObserver2
                .assertNoErrors()
                .assertValue { l: List<Expense> -> l.size == 1  }

    }

    private fun createExpensesList(): List<ExpenseSchema> {
        return listOf<ExpenseSchema>(
                Gson().fromJson("{\"id\":\"5b996064dfd5b783915112f5\",\"amount\":{\"value\":\"1854.99\",\"currency\":\"EUR\"},\"date\":\"2018-09-10T02:11:29.184Z\",\"merchant\":\"KAGE\",\"receipts\":[],\"comment\":\"\",\"category\":\"\",\"user\":{\"first\":\"Vickie\",\"last\":\"Lee\",\"email\":\"vickie.lee@pleo.io\"}}", ExpenseSchema::class.java),
                Gson().fromJson("{\"id\":\"5b996064ba218563e3ed5935\",\"amount\":{\"value\":\"3383.76\",\"currency\":\"DKK\"},\"date\":\"2018-03-23T08:31:02.663Z\",\"merchant\":\"ELEMANTRA\",\"receipts\":[],\"comment\":\"\",\"category\":\"\",\"user\":{\"first\":\"Frances\",\"last\":\"Atkins\",\"email\":\"frances.atkins@pleo.io\"}}", ExpenseSchema::class.java),
                Gson().fromJson("{\"id\":\"5b99606492951fe4481be7c6\",\"amount\":{\"value\":\"2069.83\",\"currency\":\"EUR\"},\"date\":\"2018-02-22T16:25:40.540Z\",\"merchant\":\"EMERGENT\",\"receipts\":[],\"comment\":\"\",\"category\":\"\",\"user\":{\"first\":\"Christensen\",\"last\":\"Trevino\",\"email\":\"christensen.trevino@pleo.io\"}}", ExpenseSchema::class.java)
        )
    }

    private fun createCachedExpensesList(): List<ExpenseSchema> {
        return listOf<ExpenseSchema>(
                Gson().fromJson("{\"id\":\"5b996064dfd5b783915112f5\",\"amount\":{\"value\":\"1854.99\",\"currency\":\"EUR\"},\"date\":\"2018-09-10T02:11:29.184Z\",\"merchant\":\"KAGE\",\"receipts\":[],\"comment\":\"\",\"category\":\"\",\"user\":{\"first\":\"Vickie\",\"last\":\"Lee\",\"email\":\"vickie.lee@pleo.io\"}}", ExpenseSchema::class.java)
        )
    }
}