package mx.mauriciogs.mibanca.transactions

import androidx.fragment.app.Fragment
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.MytransactionsFragmentBinding
import mx.mauriciogs.mibanca.extensions.viewBinding

class MyTransactionsFragment: Fragment(R.layout.mytransactions_fragment) {

    private val binding: MytransactionsFragmentBinding by viewBinding { MytransactionsFragmentBinding.bind(requireView()) }
}
