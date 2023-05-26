package mx.mauriciogs.mibanca.payments

import mx.mauriciogs.storage.cards.domain.model.Cards
import mx.mauriciogs.storage.common.empty
import mx.mauriciogs.storage.payments.domain.model.Payments

data class PaymentsUIModel (val showProgress: Boolean,
                            val showError: Exception?,
                            val showCardsAvailable: MutableList<Cards>?,
                            val validData: Boolean,
                            val paymentSuccess: Boolean)

data class Payment ( var id: Int = 0,
                     var cardNumberHolder: String = String.empty(),
                     var recipientsCardNumber: String = String.empty(),
                     var recipientsName: String = String.empty(),
                     var paymentReason: String = String.empty(),
                     var date: String = String.empty(),
                     var hour: String = String.empty(),
                     var location: String = String.empty())

fun Payment.toPayments() = Payments(
    id = id,
    cardNumberHolder = cardNumberHolder,
    recipientsCardNumber = recipientsCardNumber,
    recipientsName = recipientsName,
    paymentReason = paymentReason,
    date = date,
    hour = hour,
    location = location
)