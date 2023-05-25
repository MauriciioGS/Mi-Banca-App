package mx.mauriciogs.mibanca.location

import android.content.Context
import android.location.LocationManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

fun Fragment.liveDataGPS(dispatcher: CoroutineDispatcher): LiveData<Boolean> {

    val isEnabled = MutableLiveData<Boolean>()

    lifecycleScope.launch(dispatcher) {
        val mLocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var mGPSEnabled = false

        while (!mGPSEnabled) {
            mGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            isEnabled.postValue(mGPSEnabled)
        }
    }

    return isEnabled
}