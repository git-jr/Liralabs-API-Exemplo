package example.com.repository

import example.com.database.dao.UserDao
import example.com.dto.UserResponse
import example.com.model.User

class UserRepository(
    private val dao: UserDao = UserDao()
) {
    suspend fun getAllUsers(): List<UserResponse> = dao.findAll()

    suspend fun getUserById(idUser: Int): User? = dao.findById(idUser)

    suspend fun getUserByEmail(email: String): User? = dao.findByEmail(email)

    suspend fun getUserByEmailAndPassword(email: String, password: String): User? = dao.findByEmailAndPassword(email, password)

    suspend fun getUserIdByEmailAndPassword(email: String, password: String): Int? = dao.getUserIdByEmailAndPassword(email, password)

    suspend fun saveUser(user: User): User = dao.save(user)

    suspend fun updateUser(user: User): Boolean = dao.update(user)

    suspend fun updateUserImage(idUser: Int, image: String): Boolean = dao.updateImage(idUser, image)

    suspend fun updateUserPassword(idUser: Int, password: String): Boolean = dao.updatePassword(idUser, password)

    suspend fun deleteUser(idUser: Int): Boolean = dao.delete(idUser)
}
