package example.com.repository

import example.com.database.dao.UserDao
import example.com.dto.UserResponse
import example.com.model.User

class UserRepository(
    private val dao: UserDao = UserDao()
) {
    suspend fun getAllUsers(): List<UserResponse> = dao.findAll()

    suspend fun getUserByEmail(email: String): User? = dao.findByEmail(email)

    suspend fun saveUser(user: User): User = dao.save(user)

    suspend fun saveAllUsers(users: List<User>) = dao.saveAll(users)

    suspend fun updateUserPassword(email: String, newPassword: String): Boolean {
        return dao.updatePassword(email, newPassword)
    }

    suspend fun deleteUser(email: String): Boolean {
        return dao.delete(email)
    }
}
