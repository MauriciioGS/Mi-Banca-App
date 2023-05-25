package mx.mauriciogs.storage.account.data.datasource.local

import mx.mauriciogs.storage.account.data.datasource.local.entity.UserProfile
import mx.mauriciogs.storage.common.Result
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(private val userDao: UserDao) {

    suspend fun insertUser(userProfile: UserProfile) = userDao.insertUser(userProfile)

    suspend fun getUser(): Result<UserProfile> {
        val userEntity = userDao.getProfile()
        return if (userEntity != null) Result.Success(userEntity) else Result.Error(AuthenticationException())
    }
}

class AuthenticationException(override val message: String = "User not logged in") : Exception(message)
