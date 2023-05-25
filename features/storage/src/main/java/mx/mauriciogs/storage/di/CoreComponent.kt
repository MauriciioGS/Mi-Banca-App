package mx.mauriciogs.storage.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import mx.mauriciogs.storage.coroutines.di.CoroutinesDispatchersModule
import javax.inject.Singleton

@Component(modules = [CoroutinesDispatchersModule::class])
@Singleton
interface CoreComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CoreComponent
    }
}
