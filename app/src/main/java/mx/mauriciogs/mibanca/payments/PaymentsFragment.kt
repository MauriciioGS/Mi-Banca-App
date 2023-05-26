package mx.mauriciogs.mibanca.payments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.cards.mycards.MyCardsException
import mx.mauriciogs.mibanca.databinding.PaymentsFragmentBinding
import mx.mauriciogs.mibanca.extensions.*
import mx.mauriciogs.mibanca.location.liveDataGPS
import mx.mauriciogs.mibanca.location.permission.PermissionRequester
import mx.mauriciogs.mibanca.payments.PaymentExceptionHandler.*
import mx.mauriciogs.storage.cards.domain.model.Cards
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PaymentsFragment: Fragment(R.layout.payments_fragment) {

    private val binding: PaymentsFragmentBinding by viewBinding { PaymentsFragmentBinding.bind(requireView()) }

    private val paymentsViewModel: PaymentsViewModel by viewModels()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder
    private var paymentLocation = String.empty()

    private val fineLocation = PermissionRequester(this,
        Manifest.permission.ACCESS_FINE_LOCATION,
        onDenied = {
            Toast.makeText(requireActivity(), getString(R.string.accept_permissions), Toast.LENGTH_LONG).show()
        },
        onShowRationale = {
            Toast.makeText(requireActivity(), getString(R.string.accept_permissions), Toast.LENGTH_LONG).show()
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fineLocation.runWithPermission { initGPS() }
        paymentsViewModel.getMyCards()
        initObserver()
        initUI()
    }

    private fun initObserver() {
        liveDataObserve(paymentsViewModel.paymentsUIModelState) { paymentsUi(it ?:return@liveDataObserve) }
    }

    private fun paymentsUi(paymentsUIModel: PaymentsUIModel) = paymentsUIModel.run {
        binding.progressIndicator.hideOrShow(showProgress)
        if (showError != null) showErrorUi(showError)
        if (showCardsAvailable != null) showMyCards(showCardsAvailable)
        if (validData) getPaymentMetaData()
        if (paymentSuccess) showPaymentSuccess()
    }

    private fun showPaymentSuccess() {
        Toast.makeText(requireActivity(), getString(R.string.payment_success), Toast.LENGTH_SHORT).show()
        with(binding) {
            textInputCardNumber.editText?.setText("")
            textInputRecipientName.editText?.setText("")
            textInputPaymentReason.editText?.apply {
                setText("")
                clearFocus()
            }
        }
    }

    private fun showMyCards(showCardsAvailable: MutableList<Cards>) {
        val cardOptions = arrayListOf<String>()
        showCardsAvailable.forEach {
            cardOptions.add("**${it.cardNumber.slice(12..it.cardNumber.lastIndex)}")
        }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, cardOptions)
        binding.editTextCardSelection.setAdapter(adapter)
    }

    private fun showErrorUi(showError: Exception) {
        when(showError) {
            is MyCardsException.NoDataFound -> Toast.makeText(requireActivity(), showError.error, Toast.LENGTH_SHORT).show()
            is NoCardSelected -> Toast.makeText(requireActivity(), showError.error, Toast.LENGTH_SHORT).show()
            is NoReceiptCard -> Toast.makeText(requireActivity(), showError.error, Toast.LENGTH_SHORT).show()
            is NoReceiptName -> Toast.makeText(requireActivity(), showError.error, Toast.LENGTH_SHORT).show()
            is NoReason -> Toast.makeText(requireActivity(), showError.error, Toast.LENGTH_SHORT).show()
            is LocationError -> Toast.makeText(requireActivity(), showError.error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initUI() {
        with(binding) {
            buttonPay.setOnClickListener {
                val numCardHidden = editTextCardSelection.text.toString()
                val recipientsCardNumber = textInputCardNumber.editText?.text.toString()
                val recipientName = textInputRecipientName.editText?.text.toString()
                val reason = textInputPaymentReason.editText?.text.toString()
                paymentsViewModel.validateData(numCardHidden, recipientsCardNumber, recipientName, reason)
            }
        }
    }

    private fun getPaymentMetaData() {
        val sdf = SimpleDateFormat("'Fecha: 'dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        val sdfT = SimpleDateFormat("'Hora: 'HH:mm:ss z")
        val currentTime = sdfT.format(Date())
        if (paymentLocation.isNotEmpty())
            paymentsViewModel.processPayment(currentTime, currentDate, paymentLocation)
        else
            getLocation(false)
    }

    private fun initGPS() {
        if (isTrueGPS()) { // GPS enabled
            getLocation()
        } else { // GPS disabled
            val isGPS2 = liveDataGPS(Dispatchers.IO)

            isGPS2.nonNullObserve(viewLifecycleOwner) { gpsEnabled ->
                if (gpsEnabled) {
                    isGPS2.removeObservers(viewLifecycleOwner)
                    getLocation()
                }
            }
            showLocationDialog()
        }
    }


    private fun getLocation(isStart: Boolean = true) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        geocoder = Geocoder(requireActivity(), Locale.getDefault())

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            getPaymentMetaData()
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
            val location: Location? = task.result
            if (location == null) {
                showErrorUi(LocationError())
            } else {
                val lat = location.latitude
                val long = location.longitude
                if (Build.VERSION.SDK_INT >= 33) {
                    geocoder.getFromLocation(lat, long, 1) {
                        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                            val address = it[0].getAddressLine(0)
                            if (address != null) {
                                paymentLocation = address
                                if (!isStart) getPaymentMetaData()
                            }
                            else showErrorUi(LocationError())
                        }
                    }
                } else {
                    val locationGeo = geocoder.getFromLocation(lat, long, 1)
                    if (locationGeo != null) {
                        val address = locationGeo[0].getAddressLine(0)
                        if (address != null) paymentLocation = address
                        else showErrorUi(LocationError())
                    }
                }
            }
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
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onStop() {
        super.onStop()
        paymentsViewModel.clearUiState()
    }

}
