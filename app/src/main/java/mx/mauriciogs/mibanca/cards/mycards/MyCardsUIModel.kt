package mx.mauriciogs.mibanca.cards.mycards

import mx.mauriciogs.storage.cards.domain.model.Cards

data class MyCardsUIModel (val showProgress: Boolean,
                           val showError: Exception?,
                           val showSuccessCards: MutableList<Cards>?)