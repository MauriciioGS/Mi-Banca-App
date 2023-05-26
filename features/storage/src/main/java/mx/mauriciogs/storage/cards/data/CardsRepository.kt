package mx.mauriciogs.storage.cards.data

import mx.mauriciogs.storage.cards.data.datasource.local.CardsLocalDataSource
import mx.mauriciogs.storage.cards.data.datasource.local.entity.toCards
import mx.mauriciogs.storage.cards.domain.model.Cards
import mx.mauriciogs.storage.cards.domain.model.toCardsEntity
import javax.inject.Inject

class CardsRepository @Inject constructor(private val cardsLocalDataSource: CardsLocalDataSource) {

    suspend fun saveNewCard(card: Cards) = cardsLocalDataSource.insertCard(card.toCardsEntity())

    suspend fun getAllCards(): MutableList<Cards> {
        val cards = cardsLocalDataSource.getAllCards()
        val cardsDomain = mutableListOf<Cards>()
        cards.forEach {
            cardsDomain.add(it.toCards())
        }
        return cardsDomain
    }
}
