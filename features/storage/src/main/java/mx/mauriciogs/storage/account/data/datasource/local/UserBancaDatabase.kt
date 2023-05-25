package mx.mauriciogs.storage.account.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.mauriciogs.storage.cards.data.datasource.local.entity.Cards
import mx.mauriciogs.storage.payments.data.datasource.local.entity.Payments
import mx.mauriciogs.storage.account.data.datasource.local.entity.UserProfile
import mx.mauriciogs.storage.cards.data.datasource.local.CardsDao
import mx.mauriciogs.storage.payments.data.datasource.local.PaymentsDao

@Database(entities = [UserProfile::class, Cards::class, Payments::class], version = DATABASE_USER_VERSION, exportSchema = false)
abstract class UserBancaDatabase: RoomDatabase() {

    abstract val userDao: UserDao
    abstract val cardsDao: CardsDao
    abstract val paymentsDao: PaymentsDao

    companion object {

        @Volatile
        private var INSTANCE: UserBancaDatabase? = null

        fun getDatabase(context: Context) = build(context)

        fun build(context: Context) = Room.databaseBuilder(context, UserBancaDatabase::class.java, DATABASE_BANCA_NAME)
    }
}
