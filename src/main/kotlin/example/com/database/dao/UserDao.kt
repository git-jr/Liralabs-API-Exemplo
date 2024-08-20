package example.com.database.dao

import example.com.model.User
import example.com.model.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserDao {
    suspend fun findAll(): List<User> = dbQuery {
        Users.selectAll().map {
            User(
                id = it[Users.id],
                name = it[Users.name],
                password = it[Users.password],
                email = it[Users.email]
            )
        }
    }

    suspend fun save(user: User) =
        dbQuery {
            val insertStatement = Users.insert {
                it[id] = user.id
                it[name] = user.name
                it[password] = user.password
                it[email] = user.email
            }
            // Return the user that was inserted
            insertStatement.resultedValues?.singleOrNull()?.let {
                User(
                    id = it[Users.id],
                    name = it[Users.name],
                    password = it[Users.password],
                    email = it[Users.email]
                )
            }
        }

}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }