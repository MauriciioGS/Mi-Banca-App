package mx.mauriciogs.mibanca.login.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.RegisterFragmentBinding
import mx.mauriciogs.mibanca.extensions.viewBinding

class SignUpFragment: Fragment(R.layout.register_fragment) {

    private val binding by viewBinding { RegisterFragmentBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            ivClose.setOnClickListener { findNavController().popBackStack() }
        }
    }
}