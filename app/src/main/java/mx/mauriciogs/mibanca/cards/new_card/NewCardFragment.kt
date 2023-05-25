package mx.mauriciogs.mibanca.cards.new_card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.NewcardFragmentBinding
import mx.mauriciogs.mibanca.extensions.viewBinding

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
        with(binding) {

        }
    }
}
