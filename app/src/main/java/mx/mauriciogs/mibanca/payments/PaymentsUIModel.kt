package mx.mauriciogs.mibanca.payments

import mx.mauriciogs.storage.cards.domain.model.Cards

data class PaymentsUIModel (val showProgress: Boolean,
                            val showError: Exception?,
                            val showCardsAvailable: MutableList<Cards>?,
                            val paymentSuccess: Boolean)