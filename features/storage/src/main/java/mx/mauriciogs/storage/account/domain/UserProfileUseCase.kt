package mx.mauriciogs.storage.account.domain

import mx.mauriciogs.storage.account.data.UserRepository
import mx.mauriciogs.storage.account.domain.models.UserProfile
import javax.inject.Inject

class UserProfileUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun getUser() = userRepository.getUser()

    suspend fun saveUser(userProfile: UserProfile) = userRepository.saveUser(userProfile)
}
