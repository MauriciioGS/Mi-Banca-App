package mx.mauriciogs.mibanca.cards

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.MycardsFragmentBinding
import mx.mauriciogs.mibanca.extensions.nonNullObserve
import mx.mauriciogs.mibanca.extensions.viewBinding
import mx.mauriciogs.mibanca.location.liveDataGPS
import mx.mauriciogs.mibanca.location.permission.PermissionRequester

@AndroidEntryPoint
class MyCardsFragment: Fragment(R.layout.mycards_fragment) {

    private val binding by viewBinding { MycardsFragmentBinding.bind(requireView()) }

    private val coarseLocation = PermissionRequester(this,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        onDenied = {
            Toast.makeText(requireActivity(), getString(R.string.accept_permissions), Toast.LENGTH_LONG).show()
        },
        onShowRationale = {
            Toast.makeText(requireActivity(), getString(R.string.accept_permissions), Toast.LENGTH_LONG).show()
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkGPS()
    }

    private fun checkGPS() {
        coarseLocation.runWithPermission { initGPS() }
    }

    private fun initGPS() {
        if (isTrueGPS()) { // GPS enabled
            initUI()
        } else { // GPS disabled
            val isGPS2 = liveDataGPS(Dispatchers.IO)

            isGPS2.nonNullObserve(viewLifecycleOwner) { gpsEnabled ->
                if (gpsEnabled) {
                    isGPS2.removeObservers(viewLifecycleOwner)
                    initUI()
                }
            }

            showLocationDialog()
        }
    }

    private fun showLocationDialog() {
        MaterialAlertDialogBuilder(requireActivity()).apply {
            setTitle("GPS desactivado")
            setMessage("Tu ubicación está desactivada, activa el GPS para continuar utilizando los servicios de Mi Banca App")
            setCancelable(false)
            setPositiveButton("Aceptar") { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            setNegativeButton("Cancelar") { dialogInterface, _ -> dialogInterface.dismiss() }
            create()
        }.show()
    }

    private fun isTrueGPS(): Boolean {
        val mLocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun initUI() {

    }
}