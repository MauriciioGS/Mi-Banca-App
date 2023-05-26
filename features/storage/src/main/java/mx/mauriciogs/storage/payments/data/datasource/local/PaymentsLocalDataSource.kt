package mx.mauriciogs.storage.payments.data.datasource.local

import mx.mauriciogs.storage.payments.data.datasource.local.entity.Payments
import javax.inject.Inject

class PaymentsLocalDataSource @Inject constructor(private val paymentsDao: PaymentsDao) {

    suspend fun saveNewPayment(payments: Payments) = paymentsDao.insertPayment(payments)

    suspend fun getAllPayments() = paymentsDao.getAllPayments()
}
