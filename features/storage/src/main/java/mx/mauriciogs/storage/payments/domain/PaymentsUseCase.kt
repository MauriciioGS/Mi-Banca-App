package mx.mauriciogs.storage.payments.domain

import mx.mauriciogs.storage.payments.data.PaymentsRepository
import mx.mauriciogs.storage.payments.domain.model.Payments
import javax.inject.Inject

class PaymentsUseCase @Inject constructor(private val paymentsRepository: PaymentsRepository) {

    suspend fun saveNewPayment(payments: Payments) = paymentsRepository.saveNewPayment(payments)

    suspend fun getMyTransactions() = paymentsRepository.getAllPayments()
}
