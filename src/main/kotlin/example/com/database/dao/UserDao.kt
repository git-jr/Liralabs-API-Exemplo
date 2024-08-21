package example.com.database.dao

import example.com.dto.UserResponse
import example.com.dto.toUserResponse
import example.com.model.User
import example.com.model.Users
import example.com.model.Users.select
import io.ktor.http.cio.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserDao {
    suspend fun findAll(): List<UserResponse> = dbQuery {
        Users.selectAll().map {
            User(
                uid = it[Users.uid],
                cpf = it[Users.cpf],
                name = it[Users.name],
                email = it[Users.email],
                password = it[Users.password],
                imagem = it[Users.imagem]
            ).toUserResponse()
        }
    }

    suspend fun saveAll(users: List<User>) = dbQuery {
        Users.batchInsert(users) { user ->
            this[Users.cpf] = user.cpf
            this[Users.name] = user.name
            this[Users.email] = user.email
            this[Users.password] = user.password
            this[Users.imagem] = user.imagem
        }
    }

    suspend fun save(user: User): User = dbQuery {
        val insertStatement = Users.insert {
            it[cpf] = user.cpf
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password
            it[imagem] = user.imagem
        }
        insertStatement.resultedValues?.singleOrNull()?.let {
            User(
                uid = it[Users.uid],
                cpf = it[Users.cpf],
                name = it[Users.name],
                email = it[Users.email],
                password = it[Users.password],
                imagem = it[Users.imagem]
            )
        } ?: user
    }

    suspend fun findByEmail(email: String): User? = dbQuery {
        Users.select { Users.email eq email }
            .map {
                User(
                    uid = it[Users.uid],
                    cpf = it[Users.cpf],
                    name = it[Users.name],
                    email = it[Users.email],
                    password = it[Users.password],
                    imagem = it[Users.imagem]
                )
            }.singleOrNull()
    }

    suspend fun updatePassword(email: String, newPassword: String): Boolean = dbQuery {
        Users.update({ Users.email eq email }) {
            it[password] = newPassword
        } > 0
    }

    suspend fun delete(email: String): Boolean = dbQuery {
        Users.deleteWhere { Users.email eq email } > 0
    }
}


suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }