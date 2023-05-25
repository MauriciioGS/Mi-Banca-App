package mx.mauriciogs.storage.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class CoroutinesDispatchers(val main: CoroutineDispatcher = Dispatchers.Main,
                                 val default: CoroutineDispatcher = Dispatchers.Default,
                                 val io: CoroutineDispatcher = Dispatchers.IO)
