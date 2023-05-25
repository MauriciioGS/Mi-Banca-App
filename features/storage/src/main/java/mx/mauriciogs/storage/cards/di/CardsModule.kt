package mx.mauriciogs.storage.cards.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.mauriciogs.storage.cards.data.CardsRepository
import mx.mauriciogs.storage.cards.data.datasource.local.CardsDao
import mx.mauriciogs.storage.cards.data.datasource.local.CardsLocalDataSource
import mx.mauriciogs.storage.cards.domain.CardsUseCase

@Module
@InstallIn(SingletonComponent::class)
class CardsModule {

    @Provides
    fun providesCardsLocalDataSource(cardsDao: CardsDao) = CardsLocalDataSource(cardsDao)

    @Provides
    fun providesCardsRepository(cardsLocalDataSource: CardsLocalDataSource) = CardsRepository(cardsLocalDataSource)

    @Provides
    fun providesCardsUseCase(cardsRepository: CardsRepository) = CardsUseCase(cardsRepository)
}
