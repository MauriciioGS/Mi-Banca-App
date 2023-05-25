package mx.mauriciogs.mibanca.payments

import androidx.fragment.app.Fragment
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.MypaymentsFragmentBinding
import mx.mauriciogs.mibanca.extensions.viewBinding

class MyPaymentsFragment: Fragment(R.layout.mypayments_fragment) {

    private val binding: MypaymentsFragmentBinding by viewBinding { MypaymentsFragmentBinding.bind(requireView()) }

}
