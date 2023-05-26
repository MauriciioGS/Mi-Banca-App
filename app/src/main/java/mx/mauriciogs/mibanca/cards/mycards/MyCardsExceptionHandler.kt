package mx.mauriciogs.mibanca.cards.mycards

import androidx.annotation.StringRes
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.login.signup.SignUpException.FullName
import mx.mauriciogs.mibanca.login.signup.SignUpException.Password
import mx.mauriciogs.mibanca.login.signup.SignUpException.UserName
import mx.mauriciogs.mibanca.login.util.RegisterCred
import javax.inject.Inject

sealed class MyCardsException : Exception() {
    class NoDataFound(@StringRes val error: Int = R.string.no_cards_found) : MyCardsException()
}
