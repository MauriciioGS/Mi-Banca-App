package mx.mauriciogs.mibanca.cards.new_card

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.cards.new_card.NewCardExceptionHandler.*
import mx.mauriciogs.mibanca.databinding.NewcardFragmentBinding
import mx.mauriciogs.mibanca.extensions.hideOrShow
import mx.mauriciogs.mibanca.extensions.liveDataObserve
import mx.mauriciogs.mibanca.extensions.viewBinding
import mx.mauriciogs.mibanca.main.MainActivity

@AndroidEntryPoint
class NewCardFragment: Fragment(R.layout.newcard_fragment) {

    private val binding: NewcardFragmentBinding by viewBinding { NewcardFragmentBinding.bind(requireView()) }

    private val newCardViewModel: NewCardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newCardViewModel.getUser()
        initObservers()
        initUI()
    }

    private fun initObservers() {
        liveDataObserve(newCardViewModel.newCardUiModel) { newCardUi(it ?: return@liveDataObserve) }
    }

    private fun newCardUi(newCardUIModel: NewCardUIModel) = newCardUIModel.run {
        binding.progressIndicator.hideOrShow(showProgress)
        if (showError != null) showErrorUi(showError)
        if (registerSuccess) showNewCardSuccess()
    }

    private fun showNewCardSuccess() {
        Toast.makeText(requireActivity(), getString(R.string.register_success), Toast.LENGTH_LONG).show()
        findNavController().popBackStack()
    }

    private fun showErrorUi(showError: Exception) {
        when(showError) {
            is BadCardNum -> Toast.makeText(requireActivity(), showError.error, Toast.LENGTH_LONG).show()
            is BadMonth -> Toast.makeText(requireActivity(), showError.error, Toast.LENGTH_LONG).show()
            is BadYear -> Toast.makeText(requireActivity(), showError.error, Toast.LENGTH_LONG).show()
            is BadYear2 -> Toast.makeText(requireActivity(), showError.error, Toast.LENGTH_LONG).show()
        }
    }

    private fun initUI() {
        (requireActivity() as MainActivity).binding.bottomNav.visibility = View.GONE
        with(binding) {
            ivClose.setOnClickListener { findNavController().popBackStack() }

            btnNewCard.setOnClickListener { validateData() }
        }
    }

    private fun validateData() {
        with(binding) {
            val cardNum = etCardNum.text.toString()
            val month = etMonth.text.toString()
            val year = etYear.text.toString()
            newCardViewModel.registerCard(cardNum, month, year)
        }
    }
}
