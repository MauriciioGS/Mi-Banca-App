package mx.mauriciogs.mibanca.account

import mx.mauriciogs.storage.account.domain.models.UserProfile

data class MyAccountUIModel (val showProgress: Boolean,
                             val showError: Exception?,
                             val showSuccessUser: UserProfile?)