package mx.mauriciogs.mibanca.payments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.mauriciogs.mibanca.cards.mycards.MyCardsException
import mx.mauriciogs.mibanca.payments.PaymentExceptionHandler.*
import mx.mauriciogs.storage.cards.domain.CardsUseCase
import mx.mauriciogs.storage.cards.domain.model.Cards
import mx.mauriciogs.storage.coroutines.CoroutinesDispatchers
import mx.mauriciogs.storage.payments.domain.PaymentsUseCase
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(private val cardsUseCase: CardsUseCase,
                                            private val paymentsUseCase: PaymentsUseCase,
                                            private val coroutinesDispatchers: CoroutinesDispatchers): ViewModel(){

    private val _paymentsUIModelState = MutableLiveData<PaymentsUIModel>()
    val paymentsUIModelState: LiveData<PaymentsUIModel>
        get() = _paymentsUIModelState

    private var cards = mutableListOf<Cards>()

    private val payment = Payment()

    fun getMyCards() {
        viewModelScope.launch(coroutinesDispatchers.io) {
            viewModelScope.launch(coroutinesDispatchers.io) {
                val cardsList = cardsUseCase.getAllCards()
                withContext(coroutinesDispatchers.main) {
                    if (cardsList.isEmpty()) {
                        notifyUiState(showProgress = false, showError = MyCardsException.NoDataFound())
                    } else {
                        cards = cardsList
                        notifyUiState(showProgress = false, showCardsAvailable = cardsList)
                    }
                }
            }
        }
    }

    fun validateData(numCardHidden: String,
                     recipientsCardNumber: String,
                     recipientName: String,
                     reason: String) {
        when {
            numCardHidden.isEmpty() -> notifyUiState(showError = NoCardSelected())
            recipientsCardNumber.isEmpty() || recipientsCardNumber.length < 16 -> notifyUiState(showError = NoReceiptCard())
            recipientName.isEmpty() || recipientName.length < 12 -> notifyUiState(showError = NoReceiptName())
            reason.isEmpty() || reason.length < 3 -> notifyUiState(showError = NoReason())
            else -> {
                payment.cardNumberHolder = numCardHidden
                payment.recipientsCardNumber = recipientsCardNumber
                payment.recipientsName = recipientName
                payment.paymentReason = reason
                notifyUiState(validData = true)
            }
        }
    }

    fun processPayment(
        currentTime: String,
        currentDate: String,
        paymentLocation: String
    ) {
        viewModelScope.launch(coroutinesDispatchers.io) {
            payment.date = currentDate
            payment.hour = currentTime
            payment.location = paymentLocation
            cards.forEach {
                if (it.cardNumber.slice(12..it.cardNumber.lastIndex) == payment.cardNumberHolder.slice(2..payment.cardNumberHolder.lastIndex)) {
                    payment.cardNumberHolder = it.cardNumber
                    return@forEach
                }
            }
            paymentsUseCase.saveNewPayment(payment.toPayments())
            withContext(coroutinesDispatchers.main) {
                notifyUiState(showProgress = false, paymentSuccess = true)
            }
        }
    }

    fun clearUiState() {
        notifyUiState()
    }

    private fun notifyUiState(showProgress: Boolean = false,
                              showError: Exception? = null,
                              showCardsAvailable: MutableList<Cards>? = null,
                              validData: Boolean = false,
                              paymentSuccess: Boolean = false) {
        val paymentsUIModel = PaymentsUIModel(showProgress, showError, showCardsAvailable, validData, paymentSuccess)
        _paymentsUIModelState.value = paymentsUIModel
    }

}
