package mx.mauriciogs.storage.payments.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_payments")
data class Payments (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var cardNumberHolder: String,
    var recipientsCardNumber: String,
    var recipientsName: String,
    var paymentReason: String,
    var date: String,
    var hour: String,
    var location: String)

fun Payments.toPaymentsTrans() = mx.mauriciogs.storage.payments.domain.model.Payments(
    id = id,
    cardNumberHolder = cardNumberHolder,
    recipientsCardNumber = recipientsCardNumber,
    recipientsName = recipientsName,
    paymentReason = paymentReason,
    date = date,
    hour = hour,
    location = location
)