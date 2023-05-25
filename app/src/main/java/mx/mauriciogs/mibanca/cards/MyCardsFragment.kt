package mx.mauriciogs.mibanca.cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.MycardsFragmentBinding
import mx.mauriciogs.mibanca.extensions.viewBinding

@AndroidEntryPoint
class MyCardsFragment: Fragment(R.layout.mycards_fragment) {

    private val binding by viewBinding { MycardsFragmentBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}