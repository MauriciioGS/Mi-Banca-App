package mx.mauriciogs.mibanca.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.mauriciogs.mibanca.transactions.MyTransactionsException.NoDataFound
import mx.mauriciogs.storage.coroutines.CoroutinesDispatchers
import mx.mauriciogs.storage.payments.domain.PaymentsUseCase
import mx.mauriciogs.storage.payments.domain.model.Payments
import javax.inject.Inject

@HiltViewModel
class MyTransactionsViewModel @Inject constructor(private val paymentsUseCase: PaymentsUseCase,
                                                  private val coroutinesDispatchers: CoroutinesDispatchers): ViewModel() {

    private val _myTransUiModelState = MutableLiveData<MyTransactionsUIModel>()
    val myTransUiModelState: LiveData<MyTransactionsUIModel>
        get() = _myTransUiModelState

    fun getMyTransactions() {
        viewModelScope.launch(coroutinesDispatchers.io) {
            val transactions = paymentsUseCase.getMyTransactions()
            withContext(coroutinesDispatchers.main) {
                if (transactions.isEmpty()) {
                    notifyUiState(showProgress = false, showError = NoDataFound())
                } else {
                    notifyUiState(showProgress = false, showSuccess = transactions)
                }
            }
        }
    }
    fun clearUiState() {
        notifyUiState()
    }
    private fun notifyUiState(showProgress: Boolean = false, showError: Exception? = null, showSuccess: MutableList<Payments>? = null) {
        val myCardsUIModel = MyTransactionsUIModel(showProgress, showError, showSuccess)
        _myTransUiModelState.value = myCardsUIModel
    }

}
