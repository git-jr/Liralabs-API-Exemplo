package example.com.repository

import example.com.model.User
import java.util.*

class UserRepository {

    val users get() = _users.toList()

    init {
        _users.add(User(UUID.randomUUID(), "admin", "admin", "email@teste"))
    }

    fun save(user: User) {
        _users.add(user)
    }

    companion object {
        private val _users = mutableListOf<User>()
    }
}