package mx.mauriciogs.mibanca.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.mauriciogs.mibanca.login.util.RegisterCred
import mx.mauriciogs.mibanca.login.util.toUserProfile
import mx.mauriciogs.storage.account.domain.UserProfileUseCase
import mx.mauriciogs.storage.common.success
import mx.mauriciogs.storage.coroutines.CoroutinesDispatchers
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpExceptionHandler: SignUpExceptionHandler,
                                          private val coroutinesDispatchers: CoroutinesDispatchers,
                                          private val userProfileUseCase: UserProfileUseCase): ViewModel() {

    private var userRegisterCredentials = RegisterCred()

    private val _signUpUiModelState = MutableLiveData<SingUpUIModel>()

    val signUpUiModelState: LiveData<SingUpUIModel>
        get() = _signUpUiModelState

    fun setCredentials(fullName: String, userName: String, password: String) {
        userRegisterCredentials.realName = fullName
        userRegisterCredentials.userName = userName
        userRegisterCredentials.password = password
    }

    fun validateCredentials() {
        notifyUiState(showProgress = true)
        val (areInvalidData, exception) = signUpExceptionHandler.areInvalidUserCredentials(userRegisterCredentials)
        if (areInvalidData) {
            return notifyUiState(showError = exception, showProgress = false)
        }
        registerUser()
    }

    private fun registerUser() {
        viewModelScope.launch(coroutinesDispatchers.io) {
            userProfileUseCase.saveUser(userRegisterCredentials.toUserProfile())
            withContext(coroutinesDispatchers.main) {
                notifyUiState(showProgress = false, userSuccess = true)
            }
        }
    }

    fun getUser() {
        viewModelScope.launch(coroutinesDispatchers.io) {
            val user = userProfileUseCase.getUser().success()
            withContext(coroutinesDispatchers.main) {
                println(user)
            }
        }
    }

    fun notifyUiState(showProgress: Boolean = false, showError: Exception? = null, enableContinueBtn: Boolean = false, userSuccess: Boolean = false) {
        val signUpUIModel = SingUpUIModel(showProgress, showError, enableContinueBtn, userSuccess)
        _signUpUiModelState.value = signUpUIModel
    }

}
