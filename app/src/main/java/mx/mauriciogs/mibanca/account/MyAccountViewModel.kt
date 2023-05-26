package mx.mauriciogs.mibanca.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.mauriciogs.mibanca.cards.mycards.MyCardsUIModel
import mx.mauriciogs.storage.account.data.datasource.local.entity.toUserProfile
import mx.mauriciogs.storage.account.domain.UserProfileUseCase
import mx.mauriciogs.storage.account.domain.models.UserProfile
import mx.mauriciogs.storage.cards.domain.model.Cards
import mx.mauriciogs.storage.common.success
import mx.mauriciogs.storage.coroutines.CoroutinesDispatchers
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(private val userProfileUseCase: UserProfileUseCase,
                                             private val coroutinesDispatchers: CoroutinesDispatchers
): ViewModel() {

    private val _myAccountUiModelState = MutableLiveData<MyAccountUIModel>()
    val myAccountUiModelState: LiveData<MyAccountUIModel>
        get() = _myAccountUiModelState

    fun getUser() {
        viewModelScope.launch(coroutinesDispatchers.io) {
            val user = userProfileUseCase.getUser().success()?.toUserProfile()
            withContext(coroutinesDispatchers.main) {
                notifyUiState(showProgress = false, showSuccess = user)
            }
        }
    }

    private fun notifyUiState(showProgress: Boolean = false, showError: Exception? = null, showSuccess: UserProfile? = null) {
        val myAccountUIModel = MyAccountUIModel(showProgress, showError, showSuccess)
        _myAccountUiModelState.value = myAccountUIModel
    }
}
