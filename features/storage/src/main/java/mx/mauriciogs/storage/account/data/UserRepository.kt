package mx.mauriciogs.storage.account.data

import mx.mauriciogs.storage.account.data.datasource.local.UserLocalDataSource
import mx.mauriciogs.storage.account.domain.models.UserProfile
import mx.mauriciogs.storage.account.domain.models.toUserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(private val userLocalDataSource: UserLocalDataSource) {

    suspend fun getUser() = userLocalDataSource.getUser()

    suspend fun saveUser(userProfile: UserProfile) = userLocalDataSource.insertUser(userProfile.toUserEntity())
}
