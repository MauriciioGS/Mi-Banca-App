package mx.mauriciogs.mibanca.login.signin

data class SingInUIModel (val showProgress: Boolean,
                          val showError: Exception?,
                          val loginSuccess: Boolean)