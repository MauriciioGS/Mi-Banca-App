package mx.mauriciogs.mibanca.cards.mycards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.mauriciogs.mibanca.cards.mycards.MyCardsException.NoDataFound
import mx.mauriciogs.storage.cards.domain.CardsUseCase
import mx.mauriciogs.storage.cards.domain.model.Cards
import mx.mauriciogs.storage.coroutines.CoroutinesDispatchers
import javax.inject.Inject

@HiltViewModel
class MyCardsViewModel @Inject constructor(private val cardsUseCase: CardsUseCase,
                                           private val coroutinesDispatchers: CoroutinesDispatchers) : ViewModel() {

    private val _myCardsUiModelState = MutableLiveData<MyCardsUIModel>()
    val myCardsUiModelState: LiveData<MyCardsUIModel>
        get() = _myCardsUiModelState

    fun getMyCards() {
        viewModelScope.launch(coroutinesDispatchers.io) {
            val cardsList = cardsUseCase.getAllCards()
            withContext(coroutinesDispatchers.main) {
                if (cardsList.isEmpty()) {
                    notifyUiState(showProgress = false, showError = NoDataFound())
                } else {
                    notifyUiState(showProgress = false, showSuccess = cardsList)
                }
            }
        }
    }

    fun clearUiState() {
        notifyUiState()
    }

    private fun notifyUiState(showProgress: Boolean = false, showError: Exception? = null, showSuccess: MutableList<Cards>? = null) {
        val myCardsUIModel = MyCardsUIModel(showProgress, showError, showSuccess)
        _myCardsUiModelState.value = myCardsUIModel
    }
}
