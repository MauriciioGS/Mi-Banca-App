package mx.mauriciogs.storage.cards.domain.model

import mx.mauriciogs.storage.common.empty
import mx.mauriciogs.storage.cards.data.datasource.local.entity.Cards

data class Cards(var id: Int = 0,
                 var cardHolder: String = String.empty(),
                 var cardNumber: String = String.empty(),
                 var expDate: String = String.empty())

fun mx.mauriciogs.storage.cards.domain.model.Cards.toCardsEntity() = Cards(
    id = id,
    cardHolder = cardHolder,
    cardNumber = cardNumber,
    expDate = expDate
)