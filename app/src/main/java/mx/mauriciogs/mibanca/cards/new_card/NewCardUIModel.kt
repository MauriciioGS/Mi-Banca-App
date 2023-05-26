package mx.mauriciogs.mibanca.cards.new_card

data class NewCardUIModel (val showProgress: Boolean,
                           val showError: Exception?,
                           val registerSuccess: Boolean)