package mx.mauriciogs.storage.payments.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.mauriciogs.storage.cards.data.datasource.local.entity.Cards
import mx.mauriciogs.storage.payments.data.datasource.local.ConstantsDao.GET_ALL_PAYMENTS
import mx.mauriciogs.storage.payments.data.datasource.local.entity.Payments

@Dao
interface PaymentsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPayment(payments: Payments)

    @Query(GET_ALL_PAYMENTS)
    suspend fun getAllPayments(): List<Payments>
}
