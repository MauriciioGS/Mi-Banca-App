package mx.mauriciogs.mibanca.cards.mycards

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mx.mauriciogs.mibanca.R
import mx.mauriciogs.mibanca.cards.mycards.MyCardsException.NoDataFound
import mx.mauriciogs.mibanca.cards.mycards.adapters.CardsAdapter
import mx.mauriciogs.mibanca.databinding.MycardsFragmentBinding
import mx.mauriciogs.mibanca.extensions.liveDataObserve
import mx.mauriciogs.mibanca.extensions.viewBinding
import mx.mauriciogs.mibanca.main.MainActivity

@AndroidEntryPoint
class MyCardsFragment: Fragment(R.layout.mycards_fragment) {

    private val binding by viewBinding { MycardsFragmentBinding.bind(requireView()) }

    private val myCardsViewModel: MyCardsViewModel by viewModels()

    private val cardAdapter by lazy { CardsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).binding.bottomNav.visibility = View.VISIBLE
        initUI()
        initRecyclerView()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        myCardsViewModel.getMyCards()
    }

    private fun initRecyclerView() = binding.recyclerView.apply { adapter = cardAdapter }

    private fun initObserver() {
        liveDataObserve(myCardsViewModel.myCardsUiModelState) { myCardsUi(it ?: return@liveDataObserve)}
    }

    private fun myCardsUi(myCardsUIModel: MyCardsUIModel) = myCardsUIModel.run {
        if (showError != null) showErrorUi(showError)
        if (showSuccessCards != null) cardAdapter.submitList(showSuccessCards)
    }

    private fun showErrorUi(showError: Exception) {
        when(showError) {
            is NoDataFound -> Toast.makeText(requireActivity(), showError.error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initUI() {
        with(binding) {
            btnNewCard.setOnClickListener { findNavController().navigate(MyCardsFragmentDirections.actionMyCardsFragmentToNewCardFragment()) }
        }
    }

}
