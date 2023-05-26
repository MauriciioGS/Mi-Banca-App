package mx.mauriciogs.mibanca.login.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.RegisterFragmentBinding
import mx.mauriciogs.mibanca.extensions.hideOrShow
import mx.mauriciogs.mibanca.extensions.liveDataObserve
import mx.mauriciogs.mibanca.extensions.viewBinding
import mx.mauriciogs.mibanca.login.LoginActivity

@AndroidEntryPoint
class SignUpFragment: Fragment(R.layout.register_fragment) {

    private val binding by viewBinding { RegisterFragmentBinding.bind(requireView()) }

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    private fun initObservers() {
        liveDataObserve(signUpViewModel.signUpUiModelState) { signUpUiState(it ?: return@liveDataObserve) }
    }

    private fun initUI() {
        with(binding) {
            ivClose.setOnClickListener { findNavController().popBackStack() }
            btnContinue.setOnClickListener { getData() }
        }
    }

    private fun getData() {
        with(binding) {
            val fullName = etRealname.text.toString()
            val userName = etUsername.text.toString()
            val pass = etPass.text.toString()

            signUpViewModel.setCredentials(fullName, userName, pass)
            signUpViewModel.validateCredentials()
        }
    }

    private fun signUpUiState(singUpUIModelState: SingUpUIModel) = singUpUIModelState.run {
        binding.progressIndicator.hideOrShow(showProgress)
        if (showError != null) showErrorUi(showError)
        if (userSuccess) showSignUpSucces()
    }

    private fun showSignUpSucces() {
        Toast.makeText(requireActivity(), getString(R.string.success_register),  Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
        requireActivity().finish()
    }

    private fun showErrorUi(showError: Exception) {
        when (showError) {
            is SignUpException.FullName ->
                Toast.makeText(requireActivity(), getString(showError.error),  Toast.LENGTH_SHORT).show()
            is SignUpException.Password ->
                Toast.makeText(requireActivity(), getString(showError.error),  Toast.LENGTH_SHORT).show()
            is SignUpException.UserName ->
                Toast.makeText(requireActivity(), getString(showError.error),  Toast.LENGTH_SHORT).show()
            else ->
                Toast.makeText(requireActivity(), getString(R.string.register_failed),  Toast.LENGTH_SHORT).show()
        }
    }
}
