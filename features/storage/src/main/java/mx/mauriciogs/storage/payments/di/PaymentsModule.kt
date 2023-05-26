package mx.mauriciogs.storage.payments.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.mauriciogs.storage.payments.data.PaymentsRepository
import mx.mauriciogs.storage.payments.data.datasource.local.PaymentsDao
import mx.mauriciogs.storage.payments.data.datasource.local.PaymentsLocalDataSource
import mx.mauriciogs.storage.payments.domain.PaymentsUseCase

@Module
@InstallIn(SingletonComponent::class)
class PaymentsModule {

    @Provides
    fun providesPaymentsLocalDataSource(paymentsDao: PaymentsDao) = PaymentsLocalDataSource(paymentsDao)

    @Provides
    fun providesPaymentsRepository(paymentsLocalDataSource: PaymentsLocalDataSource) = PaymentsRepository(paymentsLocalDataSource)

    @Provides
    fun providesPaymentsUseCase(paymentsRepository: PaymentsRepository) = PaymentsUseCase(paymentsRepository)
}
