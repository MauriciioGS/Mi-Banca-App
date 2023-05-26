package mx.mauriciogs.mibanca.payments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.mauriciogs.mibanca.cards.mycards.MyCardsException
import mx.mauriciogs.mibanca.login.signup.SingUpUIModel
import mx.mauriciogs.storage.cards.domain.CardsUseCase
import mx.mauriciogs.storage.cards.domain.model.Cards
import mx.mauriciogs.storage.coroutines.CoroutinesDispatchers
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(private val cardsUseCase: CardsUseCase,
                                            private val coroutinesDispatchers: CoroutinesDispatchers): ViewModel(){

    private val _paymentsUIModelState = MutableLiveData<PaymentsUIModel>()
    val paymentsUIModelState: LiveData<PaymentsUIModel>
        get() = _paymentsUIModelState

    private var cards = mutableListOf<Cards>()

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

    fun notifyUiState(showProgress: Boolean = false, showError: Exception? = null, showCardsAvailable: MutableList<Cards>? = null, paymentSuccess: Boolean = false) {
        val paymentsUIModel = PaymentsUIModel(showProgress, showError, showCardsAvailable, paymentSuccess)
        _paymentsUIModelState.value = paymentsUIModel
    }

}
