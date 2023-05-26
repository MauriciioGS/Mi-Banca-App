package mx.mauriciogs.mibanca.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mx.mauriciogs.mibanca.databinding.TransactionItemBinding
import mx.mauriciogs.storage.payments.domain.model.Payments

class TransactionAdapter : ListAdapter<Payments, TransactionAdapter.TransactionViewHolder>(DiffCallback) {

    class TransactionViewHolder(private var binding: TransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (itemTransaction: Payments) {
            with(binding) {
                textCardNumber.text = "**${itemTransaction.cardNumberHolder.slice(12..itemTransaction.cardNumberHolder.lastIndex)}"
                textBeneficiaryName.text = itemTransaction.recipientsName
                val first4Dig = "${itemTransaction.recipientsCardNumber.slice(0..3)}"
                val second4Dig = "${itemTransaction.recipientsCardNumber.slice(4..7)}"
                val third4Dig = "${itemTransaction.recipientsCardNumber.slice(8..11)}"
                val last4Dig = "${itemTransaction.recipientsCardNumber.slice(12..15)}"
                textBeneficiaryCardNumber.text = "$first4Dig-$second4Dig-$third4Dig-$last4Dig"
                textPaymentReason.text = itemTransaction.paymentReason
                textLocation.text = itemTransaction.location
                textDateTime.text = "${itemTransaction.date} ${itemTransaction.hour}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder =
        TransactionViewHolder(TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val itemTransaction = getItem(position)
        holder.bind(itemTransaction)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Payments>() {
        override fun areItemsTheSame(oldItem: Payments, newItem: Payments) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Payments, newItem: Payments) = oldItem == newItem
    }

}