package mx.mauriciogs.mibanca.login.util

import mx.mauriciogs.mibanca.extensions.empty

data class RegisterCred(var realName: String = String.empty(),
                               var userName: String = String.empty(),
                               var password: String = String.empty())
