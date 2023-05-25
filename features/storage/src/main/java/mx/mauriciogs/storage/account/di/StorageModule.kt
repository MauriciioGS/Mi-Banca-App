package mx.mauriciogs.storage.account.di

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.mauriciogs.storage.BuildConfig
import mx.mauriciogs.storage.account.data.UserRepository
import mx.mauriciogs.storage.account.data.datasource.local.UserBancaDatabase
import mx.mauriciogs.storage.account.data.datasource.local.UserDao
import mx.mauriciogs.storage.account.data.datasource.local.UserLocalDataSource
import mx.mauriciogs.storage.encryption.Passphrase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    fun providesMainKeyAlias() = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    @Provides
    @Singleton
    fun providesPassphrase(application: Application, mainKeyAlias: String) =
        Passphrase(EncryptedSharedPreferences.create(BuildConfig.BANCA_DB_ENCKEY_FILE,
            mainKeyAlias,
            application,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM))

    @Provides
    @Singleton
    fun providesSupportFactory(passphrase: Passphrase) = SupportFactory(passphrase.getPassphrase())

    @Provides
    @Singleton
    fun providesBancaRoomDatabase(application: Application, supportFactory: SupportFactory) =
        UserBancaDatabase.getDatabase(application)
            .openHelperFactory(supportFactory)
            .build()

    @Provides
    fun providesUserProfileDao(userBancaRoomDatabase: UserBancaDatabase) = userBancaRoomDatabase.userDao
    @Provides
    fun providesCardsDao(userBancaRoomDatabase: UserBancaDatabase) = userBancaRoomDatabase.cardsDao
    @Provides
    fun providesPaymentsDao(userBancaRoomDatabase: UserBancaDatabase) = userBancaRoomDatabase.paymentsDao

    @Provides
    fun providesUserLocalDataSource(userDao: UserDao) = UserLocalDataSource(userDao)

    @Provides
    fun providesUserRepository(userLocalDataSource: UserLocalDataSource) = UserRepository(userLocalDataSource)
}
