package mx.mauriciogs.storage.cards.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.mauriciogs.storage.cards.data.datasource.local.ConstantsDao.DELETE_CARD
import mx.mauriciogs.storage.cards.data.datasource.local.ConstantsDao.GET_ALL_CARDS
import mx.mauriciogs.storage.cards.data.datasource.local.ConstantsDao.GET_CARD
import mx.mauriciogs.storage.cards.data.datasource.local.entity.Cards

@Dao
interface CardsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCard(card: Cards)

    @Query(GET_ALL_CARDS)
    suspend fun getAllCards(): List<Cards>

    @Query(GET_CARD)
    suspend fun getCard(cardNumber: String): Cards

    @Query(DELETE_CARD)
    suspend fun deleteCard(cardId: Int): Int
}
