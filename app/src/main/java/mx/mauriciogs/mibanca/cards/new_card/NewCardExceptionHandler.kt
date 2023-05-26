package mx.mauriciogs.mibanca.cards.new_card

import androidx.annotation.StringRes
import mx.mauriciogs.mibanca.R

sealed class NewCardExceptionHandler : Exception() {
    class BadCardNum(@StringRes val error: Int = R.string.bad_num_card) : NewCardExceptionHandler()
    class BadMonth(@StringRes val error: Int = R.string.bad_month) : NewCardExceptionHandler()
    class BadYear(@StringRes val error: Int = R.string.bad_year_1) : NewCardExceptionHandler()
    class BadYear2(@StringRes val error: Int = R.string.bad_year_2) : NewCardExceptionHandler()
}