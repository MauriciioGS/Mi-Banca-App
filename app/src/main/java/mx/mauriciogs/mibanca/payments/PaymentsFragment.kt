package mx.mauriciogs.mibanca.payments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.cards.mycards.MyCardsException
import mx.mauriciogs.mibanca.databinding.PaymentsFragmentBinding
import mx.mauriciogs.mibanca.extensions.liveDataObserve
import mx.mauriciogs.mibanca.extensions.viewBinding
import mx.mauriciogs.storage.cards.domain.model.Cards

@AndroidEntryPoint
class PaymentsFragment: Fragment(R.layout.payments_fragment) {

    private val binding: PaymentsFragmentBinding by viewBinding { PaymentsFragmentBinding.bind(requireView()) }

    private val paymentsViewModel: PaymentsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paymentsViewModel.getMyCards()
        initObserver()
        initUI()
    }

    private fun initObserver() {
        liveDataObserve(paymentsViewModel.paymentsUIModelState) { paymentsUi(it ?:return@liveDataObserve) }
    }

    private fun paymentsUi(paymentsUIModel: PaymentsUIModel) = paymentsUIModel.run {
        if (showError != null) showErrorUi(showError)
        if (showCardsAvailable != null) showMyCards(showCardsAvailable)
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
        }
    }

    private fun initUI() {
        with(binding) {
            buttonPay.setOnClickListener {  }
        }
    }

}
