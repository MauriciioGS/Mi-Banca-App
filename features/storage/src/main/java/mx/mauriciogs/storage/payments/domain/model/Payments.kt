package mx.mauriciogs.storage.payments.domain.model

import mx.mauriciogs.storage.common.empty

data class Payments(var id: Int = 0,
                    var cardNumberHolder: String = String.empty(),
                    var recipientsCardNumber: String = String.empty(),
                    var recipientsName: String = String.empty(),
                    var paymentReason: String = String.empty(),
                    var date: String = String.empty(),
                    var hour: String = String.empty(),
                    var location: String = String.empty())

fun Payments.toPaymentsEntity() = mx.mauriciogs.storage.payments.data.datasource.local.entity.Payments(
    id = id,
    cardNumberHolder = cardNumberHolder,
    recipientsName = recipientsName,
    recipientsCardNumber = recipientsCardNumber,
    paymentReason = paymentReason,
    date = date,
    hour = hour,
    location = location
)