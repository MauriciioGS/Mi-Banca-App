package mx.mauriciogs.mibanca.transactions

import android.os.Bundle
import android.view.View
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

    private fun initRecyclerView() = binding.recyclerView.apply { adapter = transactionAdapter }

    private fun initObserver() {

    }
}
