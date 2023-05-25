package mx.mauriciogs.mibanca.login.signin

import androidx.annotation.StringRes
import mx.mauriciogs.mibanca.R

sealed class SignInException : Exception() {
    class LoginError(@StringRes val error: Int = R.string.error_login) : SignInException()
    class NoData(@StringRes val error: Int = R.string.no_data_error) : SignInException()
    class NoExists(@StringRes val error: Int = R.string.no_user_error) : SignInException()
}