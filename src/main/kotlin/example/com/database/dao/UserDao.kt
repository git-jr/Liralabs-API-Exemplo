package example.com.database.dao

import example.com.model.User
import example.com.model.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class UserDao {
    suspend fun getUserEmail(email: String): User? = dbQuery {
        Users.selectAll().where { Users.email eq email }
            .map {
                User(
                    name = it[Users.name],
                    email = it[Users.email],
                    password = it[Users.password],
                )
            }.firstOrNull()
    }

    suspend fun delete(email: String): Boolean {
        return dbQuery {
            Users.deleteWhere { Users.email eq email } > 0
        }
    }

    suspend fun updateEmail(email: String, newName: String): Boolean {
        return dbQuery {
            Users.update({ Users.email eq email }) {
                it[Users.name] = newName
            } > 0
        }
    }

    suspend fun updatePassword(email: String, newPassword: String): Boolean {
        return dbQuery {
            Users.update({ Users.email eq email }) {
                it[Users.password] = newPassword
            } > 0
        }
    }

    suspend fun save(user: User) =
        dbQuery {
            val insertStatement = Users.insert {
                it[name] = user.name
                it[password] = user.password
                it[email] = user.email
            }
            // Return the user that was inserted
            insertStatement.resultedValues?.singleOrNull()?.let {
                User(
                    name = it[Users.name],
                    email = it[Users.email],
                    password = it[Users.password],
                )
            }
        }
}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }