package example.com.repository

import example.com.model.User

class UserRepository {

    val users get() = _users.toList()

    init {
        _users.add(User("admin", "admin", ""))
    }

    fun save(user: User) {
        _users.add(user)
    }

    companion object {
        private val _users = mutableListOf<User>()
    }
}