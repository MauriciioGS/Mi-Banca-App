package mx.mauriciogs.mibanca.login.signup

data class SingUpUIModel (val showProgress: Boolean,
                          val showError: Exception?,
                          val enableContinueBtn: Boolean,
                          val userSuccess: Boolean)