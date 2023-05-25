package mx.mauriciogs.mibanca.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>> LifecycleOwner.liveDataObserve(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner){ it?.let(observer) }
}