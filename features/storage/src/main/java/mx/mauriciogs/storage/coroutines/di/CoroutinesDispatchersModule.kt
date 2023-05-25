package mx.mauriciogs.storage.coroutines.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.mauriciogs.storage.coroutines.CoroutinesDispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesDispatchersModule {

    @Provides
    fun provideCoroutinesDispatchers() = CoroutinesDispatchers()
}
