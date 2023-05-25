package mx.mauriciogs.mibanca.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.SigninFragmentBinding
import mx.mauriciogs.mibanca.extensions.viewBinding

class SignInFragment: Fragment(R.layout.signin_fragment) {

    private val binding by viewBinding { SigninFragmentBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}