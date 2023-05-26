package mx.mauriciogs.mibanca.payments

import androidx.annotation.StringRes
import mx.mauriciogs.mibanca.R

sealed class PaymentExceptionHandler : Exception() {
    class NoCardSelected(@StringRes val error: Int = R.string.no_card_selected) : PaymentExceptionHandler()
    class NoReceiptCard(@StringRes val error: Int = R.string.no_recip_card) : PaymentExceptionHandler()
    class NoReceiptName(@StringRes val error: Int = R.string.no_recip_name) : PaymentExceptionHandler()
    class NoReason(@StringRes val error: Int = R.string.no_reason) : PaymentExceptionHandler()
    class LocationError(@StringRes val error: Int = R.string.location_error) : PaymentExceptionHandler()
}
