package mx.mauriciogs.mibanca.cards.new_card

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.mauriciogs.mibanca.cards.new_card.NewCardExceptionHandler.*
import mx.mauriciogs.mibanca.login.signin.SignInException
import mx.mauriciogs.mibanca.login.signin.SingInUIModel
import mx.mauriciogs.storage.account.domain.UserProfileUseCase
import mx.mauriciogs.storage.cards.domain.CardsUseCase
import mx.mauriciogs.storage.cards.domain.model.Cards
import mx.mauriciogs.storage.common.empty
import mx.mauriciogs.storage.common.success
import mx.mauriciogs.storage.coroutines.CoroutinesDispatchers
import javax.inject.Inject

@HiltViewModel
class NewCardViewModel @Inject constructor(private val cardsUseCase: CardsUseCase,
                                           private val userUseCase: UserProfileUseCase,
                                           private val coroutinesDispatchers: CoroutinesDispatchers
): ViewModel() {

    private val _newCardUiModel = MutableLiveData<NewCardUIModel>()

    val newCardUiModel: LiveData<NewCardUIModel>
        get() = _newCardUiModel

    private var userFullName = String.empty()

    fun getUser() {
        viewModelScope.launch(coroutinesDispatchers.io) {
            val user = userUseCase.getUser().success()
            withContext(coroutinesDispatchers.main) {
                if (user != null) {
                    userFullName = user.userRealName
                } else {
                    notifyUiState(showError = SignInException.NoExists())
                }
            }
        }
    }

    fun registerCard(cardNum: String, month: String, year: String) {
        notifyUiState(showProgress = true)
        when {
            cardNum.isEmpty() || cardNum.length < 16 -> notifyUiState(showProgress = false, showError = BadCardNum())
            month.isEmpty() || month.length < 2 || month.toInt() > 12 || month.toInt() < 1 -> notifyUiState(showProgress = false, showError = BadMonth())
            year.isEmpty() || year.length < 4 || year.toInt() <= 2023 -> notifyUiState(showProgress = false, showError = BadYear())
            else -> {
                viewModelScope.launch(coroutinesDispatchers.io) {
                    val expDate = "$month/$year"
                    cardsUseCase.saveNewCard(Cards(cardHolder = userFullName, cardNumber = cardNum, expDate = expDate))
                    withContext(coroutinesDispatchers.main) {
                        notifyUiState(showProgress = false, registerSuccess = true)
                    }
                }
            }
        }
    }

    private fun notifyUiState(showProgress: Boolean = false, showError: Exception? = null, registerSuccess: Boolean = false) {
        val newCardUiModelS = NewCardUIModel(showProgress, showError, registerSuccess)
        _newCardUiModel.value = newCardUiModelS
    }
}
