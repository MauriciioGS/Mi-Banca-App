package mx.mauriciogs.mibanca.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.mauriciogs.mibanca.login.signup.SignUpExceptionHandler
import mx.mauriciogs.mibanca.login.util.RegisterCred
import mx.mauriciogs.mibanca.login.util.toUserProfile
import mx.mauriciogs.storage.account.domain.UserProfileUseCase
import mx.mauriciogs.storage.common.success
import mx.mauriciogs.storage.coroutines.CoroutinesDispatchers
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val coroutinesDispatchers: CoroutinesDispatchers,
                                          private val userProfileUseCase: UserProfileUseCase): ViewModel() {

    private var userRegisterCredentials = RegisterCred()

    private val _signInUiModelState = MutableLiveData<SingInUIModel>()

    val signInUiModelState: LiveData<SingInUIModel>
        get() = _signInUiModelState

    fun signInUser(userInput: String, pass: String) {
        notifyUiState(showProgress = true)
        if (userInput.isEmpty() || pass.isEmpty()) notifyUiState(showError = SignInException.NoData())
        else {
            viewModelScope.launch(coroutinesDispatchers.io) {
                val user = getUser()
                withContext(coroutinesDispatchers.main) {
                    if (user != null) {
                        if ((user.userName == userInput) && (user.password == pass))
                            notifyUiState(showProgress = false, loginSuccess = true)
                        else
                            notifyUiState(showProgress = false, showError = SignInException.LoginError())
                    }
                    else notifyUiState(showProgress = false, showError = SignInException.NoExists())
                }
            }
        }
    }

    suspend fun getUser() = withContext(coroutinesDispatchers.io) {
        return@withContext userProfileUseCase.getUser().success()
    }

    fun notifyUiState(showProgress: Boolean = false, showError: Exception? = null, loginSuccess: Boolean = false) {
        val signUpUIModel = SingInUIModel(showProgress, showError, loginSuccess)
        _signInUiModelState.value = signUpUIModel
    }

}
