package mx.mauriciogs.mibanca.cards.mycards.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mx.mauriciogs.mibanca.databinding.CardItemBinding
import mx.mauriciogs.storage.cards.domain.model.Cards

class CardsAdapter : ListAdapter<Cards, CardsAdapter.CardsViewHolder>(DiffCallback) {

    class CardsViewHolder(private var binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (itemCard: Cards) {
            with(binding) {
                val end = itemCard.cardNumber.lastIndex
                textCardNumber.text = "**${itemCard.cardNumber.slice(12..end)}"
                textExpirationDate.text = itemCard.expDate
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder =
        CardsViewHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val itemCard = getItem(position)
        holder.bind(itemCard)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Cards>() {
        override fun areItemsTheSame(oldItem: Cards, newItem: Cards) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Cards, newItem: Cards) = oldItem == newItem
    }

}
