package mx.mauriciogs.mibanca.account

import androidx.fragment.app.Fragment
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.MyaccountFragmentBinding
import mx.mauriciogs.mibanca.extensions.viewBinding

class MyAccountFragment: Fragment(R.layout.myaccount_fragment) {

    private val binding: MyaccountFragmentBinding by viewBinding { MyaccountFragmentBinding.bind(requireView()) }
}
