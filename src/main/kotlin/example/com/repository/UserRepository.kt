package example.com.repository

import example.com.database.dao.UserDao
import example.com.model.User

class UserRepository(
    private val dao: UserDao = UserDao()
) {
    suspend fun getAll() = dao.findAll()

    suspend fun updateUser(user: User) = dao.update(user)

    suspend fun getUserByEmailAndPassword(email: String, password: String) = dao.findByEmailAndPassword(email, password)

    suspend fun getUserById(id: String) = dao.findById(id)

    suspend fun saveImage(id: String, img: String) = dao.saveImage(id, img)

    suspend fun save(user: User) = dao.save(user)

    suspend fun delete(id: String) = dao.delete(id)
}