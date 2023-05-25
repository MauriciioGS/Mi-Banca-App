package mx.mauriciogs.storage.cards.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_cards")
data class Cards (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var cardHolder: String,
    var cardNumber: String,
    var expDate: String)
