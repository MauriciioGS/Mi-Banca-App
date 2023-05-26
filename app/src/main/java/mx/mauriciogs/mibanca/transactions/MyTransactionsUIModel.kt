package mx.mauriciogs.mibanca.transactions

import mx.mauriciogs.storage.payments.domain.model.Payments

data class MyTransactionsUIModel (val showProgress: Boolean,
                                  val showError: Exception?,
                                  val showSuccessTransactions: MutableList<Payments>?)