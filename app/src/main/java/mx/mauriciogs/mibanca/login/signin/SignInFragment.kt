package mx.mauriciogs.mibanca.login.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.SigninFragmentBinding
import mx.mauriciogs.mibanca.extensions.hideOrShow
import mx.mauriciogs.mibanca.extensions.liveDataObserve
import mx.mauriciogs.mibanca.extensions.viewBinding
import mx.mauriciogs.mibanca.main.MainActivity

@AndroidEntryPoint
class SignInFragment: Fragment(R.layout.signin_fragment) {

    private val binding by viewBinding { SigninFragmentBinding.bind(requireView()) }

    private val signInViewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserver()
    }

    private fun initObserver() {
        liveDataObserve(signInViewModel.signInUiModelState) { signInUi(it ?: return@liveDataObserve) }
    }

    private fun initUI() {
        with(binding) {
            btnContinue.setOnClickListener {
                getCredentials()
            }

            btnRegister.setOnClickListener {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
            }
        }
    }

    private fun getCredentials() {
        with(binding) {
            val user = etUsername.text.toString()
            val pass = etPass.text.toString()
            signInViewModel.signInUser(user, pass)
        }
    }

    private fun signInUi(singInUIModelState: SingInUIModel) = singInUIModelState.run {
        binding.progressIndicator.hideOrShow(showProgress)
        if (showError != null) showUiError(showError)
        if (loginSuccess) startActivity(Intent(requireActivity(), MainActivity::class.java)).apply { requireActivity().finish() }
    }

    private fun showUiError(error: Exception) {
        signInViewModel.clearUiState()
        when(error) {
            is SignInException.NoData -> Toast.makeText(requireActivity(), getString(error.error), Toast.LENGTH_SHORT).show()
            is SignInException.LoginError -> Toast.makeText(requireActivity(), getString(error.error), Toast.LENGTH_SHORT).show()
            is SignInException.NoExists -> Toast.makeText(requireActivity(), getString(error.error), Toast.LENGTH_SHORT).show()
            else -> {
                Toast.makeText(requireActivity(), error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        signInViewModel.clearUiState()
        with(binding) {
            etUsername.setText("")
            etPass.setText("")
        }
    }

}