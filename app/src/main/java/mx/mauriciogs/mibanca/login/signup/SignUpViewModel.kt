package mx.mauriciogs.mibanca.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mx.mauriciogs.mibanca.login.util.RegisterCred
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpExceptionHandler: SignUpExceptionHandler): ViewModel() {

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
        val (areInvalidData, exception) = signUpExceptionHandler.areInvalidUserCredentials(userRegisterCredentials)
        if (areInvalidData) {
            return notifyUiState(showError = exception)
        }
        notifyUiState(userSuccess = true)
    }

    fun notifyUiState(showProgress: Boolean = false, showError: Exception? = null, enableContinueBtn: Boolean = false, userSuccess: Boolean = false) {
        val signUpUIModel = SingUpUIModel(showProgress, showError, enableContinueBtn, userSuccess)
        _signUpUiModelState.value = signUpUIModel
    }
}