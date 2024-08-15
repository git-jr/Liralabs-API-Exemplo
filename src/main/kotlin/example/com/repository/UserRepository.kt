package example.com.repository

import example.com.database.dao.UserDao
import example.com.model.User

class UserRepository(
    private val dao: UserDao = UserDao()
) {

    suspend fun users() = dao.findAll()

    suspend fun save(user: User) = dao.save(user)
}