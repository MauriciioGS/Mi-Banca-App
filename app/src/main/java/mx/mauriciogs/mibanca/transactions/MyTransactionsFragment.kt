package mx.mauriciogs.mibanca.transactions

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.databinding.MytransactionsFragmentBinding
import mx.mauriciogs.mibanca.extensions.liveDataObserve
import mx.mauriciogs.mibanca.extensions.viewBinding

@AndroidEntryPoint
class MyTransactionsFragment: Fragment(R.layout.mytransactions_fragment) {

    private val binding: MytransactionsFragmentBinding by viewBinding { MytransactionsFragmentBinding.bind(requireView()) }

    private val myTransactionsViewModel: MyTransactionsViewModel by viewModels()

    private val transactionAdapter by lazy { TransactionAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        myTransactionsViewModel.getMyTransactions()
    }

    private fun initRecyclerView() = binding.recyclerView.apply { adapter = transactionAdapter }

    private fun initObserver() {
        liveDataObserve(myTransactionsViewModel.myTransUiModelState) { myTransactionsUiModelState(it ?: return@liveDataObserve) }
    }

    private fun myTransactionsUiModelState(myTransactionsUIModel: MyTransactionsUIModel) = myTransactionsUIModel.run {
        if (showError != null) showUiError(showError)
        if (showSuccessTransactions != null) transactionAdapter.submitList(showSuccessTransactions)
    }

    private fun showUiError(showError: Exception) {
        when (showError) {
            is MyTransactionsException.NoDataFound -> Toast.makeText(requireActivity(), showError.error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        myTransactionsViewModel.clearUiState()
    }

}
