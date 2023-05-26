package mx.mauriciogs.mibanca.account

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.MyaccountFragmentBinding
import mx.mauriciogs.mibanca.extensions.liveDataObserve
import mx.mauriciogs.mibanca.extensions.viewBinding
import mx.mauriciogs.mibanca.login.LoginActivity
import mx.mauriciogs.storage.account.domain.models.UserProfile

@AndroidEntryPoint
class MyAccountFragment: Fragment(R.layout.myaccount_fragment) {

    private val binding: MyaccountFragmentBinding by viewBinding { MyaccountFragmentBinding.bind(requireView()) }

    private val myAccountViewModel: MyAccountViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAccountViewModel.getUser()
        initObserver()
    }

    private fun initObserver() {
        liveDataObserve(myAccountViewModel.myAccountUiModelState) { myAccountUIModelState(it ?: return@liveDataObserve) }
    }

    private fun myAccountUIModelState(myAccountUIModel: MyAccountUIModel) = myAccountUIModel.run {
        if (showSuccessUser != null) initUi(showSuccessUser)
    }

    private fun initUi(user: UserProfile) {
        binding.tvFullName.text = user.fullName
        binding.tvUserName.text = "Usuario: ${user.userName}"
        binding.btnCLoseSes.setOnClickListener {
            retention()
        }
    }

    private fun retention() {
        MaterialAlertDialogBuilder(requireActivity()).apply {
            setTitle("Cerrar Sesión")
            setMessage("¿Deseas cerrar sesión?")
            setCancelable(false)
            setPositiveButton("Si") { _, _ ->
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
            setNegativeButton("Cancelar") { dialogInterface, _ -> dialogInterface.dismiss() }
            create()
        }.show()
    }
}
