package mx.mauriciogs.storage.account.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.mauriciogs.storage.account.data.datasource.local.ConstantsDao.GET_PROFILE
import mx.mauriciogs.storage.account.data.datasource.local.entity.UserProfile

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserProfile)

    @Query(GET_PROFILE)
    suspend fun getProfile(): UserProfile?
}
