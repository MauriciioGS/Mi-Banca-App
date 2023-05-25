package mx.mauriciogs.storage.account.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile (
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var userName: String,
        var password: String,
        var userRealName: String)
