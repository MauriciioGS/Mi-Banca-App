package mx.mauriciogs.storage.payments.data

import mx.mauriciogs.storage.payments.data.datasource.local.PaymentsLocalDataSource
import mx.mauriciogs.storage.payments.data.datasource.local.entity.toPaymentsTrans
import mx.mauriciogs.storage.payments.domain.model.Payments
import mx.mauriciogs.storage.payments.domain.model.toPaymentsEntity
import javax.inject.Inject

class PaymentsRepository @Inject constructor(private val paymentsLocalDataSource: PaymentsLocalDataSource) {

    suspend fun saveNewPayment(payments: Payments) = paymentsLocalDataSource.saveNewPayment(payments.toPaymentsEntity())

    suspend fun getAllPayments(): MutableList<Payments> {
        val payments = paymentsLocalDataSource.getAllPayments()
        val transactions = mutableListOf<Payments>()
        payments.forEach {
            transactions.add(it.toPaymentsTrans())
        }
        return transactions
    }
}
