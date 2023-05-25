package mx.mauriciogs.mibanca.login.util

import mx.mauriciogs.mibanca.extensions.empty
import mx.mauriciogs.storage.account.domain.models.UserProfile

data class RegisterCred(var realName: String = String.empty(),
                               var userName: String = String.empty(),
                               var password: String = String.empty())

fun RegisterCred.toUserProfile() = UserProfile(
    fullName = realName,
    userName = userName,
    password = password
)
