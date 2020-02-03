package pl.ddudek.mvxrnexample.view.expensedetails

import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import pl.ddudek.mvxrnexample.model.Expense
import pl.ddudek.mvxrnexample.networking.mapToModel
import pl.ddudek.mvxrnexample.networking.schema.responses.ExpenseSchema
import pl.ddudek.mvxrnexample.usecase.AddExpenseReceiptUseCase
import pl.ddudek.mvxrnexample.usecase.UpdateExpenseCommentUseCase
import pl.ddudek.mvxrnexample.utils.RxImmediateSchedulerRule
import pl.ddudek.mvxrnexample.view.Navigator
import pl.ddudek.mvxrnexample.view.utils.TakePhotoUtil

class ExpenseDetailsPresenterTest {

    @Rule
    @JvmField
    var rule2 = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    var rule = MockitoJUnit.rule()

    @Mock
    lateinit var updateCommentUseCase: UpdateExpenseCommentUseCase

    @Mock
    lateinit var addReceiptUseCase: AddExpenseReceiptUseCase

    @Mock
    lateinit var navigator: Navigator

    @Mock
    lateinit var takePhotoNavigator: TakePhotoUtil

    @Mock
    lateinit var view: ExpenseDetailsView

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    private fun createInitialViewState(): ExpenseDetailsView.ViewState {
        return ExpenseDetailsView.ViewState(
                createExpense(),
                isEditing = false,
                savingComment = false,
                savingCommentError = null,
                uploadingReceipt = false,
                uploadingReceiptError = null)
    }

    @Test
    fun `test start editing`() {
        // given
        `when`(view.getViewState()).thenReturn(createInitialViewState())

        val presenter = ExpenseDetailsPresenter(updateCommentUseCase, addReceiptUseCase, navigator, takePhotoNavigator)
        presenter.bindView(view)

        // when
        presenter.onEditClicked()

        // then
        verify(view, times(1)).showEditCommentKeyboard()
        verify(view, times(1)).applyViewState(argForWhich {isEditing == true})
    }

    @Test
    fun `test process photo`() {
        // given
        `when`(view.getViewState()).thenReturn(createInitialViewState())
        `when`(addReceiptUseCase.run(any(), any())).thenReturn(Single.just(createExpense()))

        val presenter = ExpenseDetailsPresenter(updateCommentUseCase, addReceiptUseCase, navigator, takePhotoNavigator)
        presenter.bindView(view)

        // when
        presenter.onPhotoReady("/some/path")

        // then
        verify(view, atLeastOnce()).applyViewState(argForWhich {uploadingReceipt == true})

        verify(view, atLeastOnce()).applyViewState(argForWhich {uploadingReceipt == false && uploadingReceiptError == null})
    }

    private fun createExpense(): Expense {
        return Gson().fromJson("{\"id\":\"5b99606492951fe4481be7c6\",\"amount\":{\"value\":\"2069.83\",\"currency\":\"EUR\"},\"date\":\"2018-02-22T16:25:40.540Z\",\"merchant\":\"EMERGENT\",\"receipts\":[],\"comment\":\"\",\"category\":\"\",\"user\":{\"first\":\"Christensen\",\"last\":\"Trevino\",\"email\":\"christensen.trevino@pleo.io\"}}", ExpenseSchema::class.java).mapToModel()
    }
}