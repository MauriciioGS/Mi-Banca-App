package mx.mauriciogs.storage.cards.domain

import mx.mauriciogs.storage.cards.data.CardsRepository
import mx.mauriciogs.storage.cards.domain.model.Cards
import javax.inject.Inject

class CardsUseCase @Inject constructor(private val cardsRepository: CardsRepository) {

    suspend fun saveNewCard(card: Cards) = cardsRepository.saveNewCard(card)

    suspend fun getAllCards() = cardsRepository.getAllCards()
}
