package mx.mauriciogs.storage.account.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import mx.mauriciogs.storage.common.empty
import mx.mauriciogs.storage.account.data.datasource.local.entity.UserProfile

@Parcelize
data class UserProfile (var id: Int = 0,
                        var fullName: String = String.empty(),
                        var userName: String = String.empty(),
                        var password: String = String.empty()): Parcelable {
}

fun mx.mauriciogs.storage.account.domain.models.UserProfile.toUserEntity() = UserProfile(
    id = id,
    userName = userName,
    userRealName = fullName,
    password = password
)