package mx.mauriciogs.mibanca.cards.new_card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.NewcardFragmentBinding
import mx.mauriciogs.mibanca.extensions.viewBinding
import mx.mauriciogs.mibanca.main.MainActivity

class NewCardFragment: Fragment(R.layout.newcard_fragment) {

    private val binding: NewcardFragmentBinding by viewBinding { NewcardFragmentBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initUI()
    }

    private fun initObservers() {

    }

    private fun initUI() {
        (requireActivity() as MainActivity).binding.bottomNav.visibility = View.GONE
        with(binding) {
            ivClose.setOnClickListener { findNavController().popBackStack() }
        }
    }
}
