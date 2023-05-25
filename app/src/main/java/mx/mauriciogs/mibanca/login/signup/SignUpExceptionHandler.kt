package mx.mauriciogs.mibanca.login.signup

import androidx.annotation.StringRes
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.login.signup.SignUpException.FullName
import mx.mauriciogs.mibanca.login.signup.SignUpException.Password
import mx.mauriciogs.mibanca.login.signup.SignUpException.UserName
import mx.mauriciogs.mibanca.login.util.RegisterCred
import javax.inject.Inject

class SignUpExceptionHandler @Inject constructor() {

    fun areInvalidUserCredentials(userCredentials: RegisterCred) = when {
        userCredentials.realName.isBlank() || userCredentials.realName.length <  CONSIDERABLE_NAME_SIZE -> Pair(true, FullName())
        userCredentials.password.isBlank() || userCredentials.password.length < MIN_CHARACTERS_PASSWORD -> Pair(true, Password())
        userCredentials.userName.isBlank() || userCredentials.userName.length < MIN_CHARACTERS_PASSWORD -> Pair(true, UserName())
        else -> Pair(false, NoValidationNeeded)
    }

    companion object {
        const val CONSIDERABLE_NAME_SIZE = 15
        const val MIN_CHARACTERS_PASSWORD = 6
    }
}

sealed class SignUpException : Exception() {
    class FullName(@StringRes val error: Int = R.string.toast_introduce_real_name) : SignUpException()
    class UserName(@StringRes val error: Int = R.string.toast_introduce_name) : SignUpException()
    class Password(@StringRes val error: Int = R.string.toast_introduce_passw) : SignUpException()
}

object NoValidationNeeded : SignUpException()
