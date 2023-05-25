package mx.mauriciogs.storage.cards.data.datasource.local

import mx.mauriciogs.storage.cards.data.datasource.local.entity.Cards
import javax.inject.Inject

class CardsLocalDataSource @Inject constructor(private val cardsDao: CardsDao) {

    suspend fun insertCard(card: Cards) = cardsDao.insertCard(card)

    suspend fun getAllCards() = cardsDao.getAllCards()

    suspend fun getCard(cardNumber: String) = cardsDao.getCard(cardNumber)

    suspend fun deleteCard(cardId: Int) = cardsDao.deleteCard(cardId)

}
