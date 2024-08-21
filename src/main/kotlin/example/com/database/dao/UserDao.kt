package example.com.database.dao

import example.com.dto.UserResponse
import example.com.dto.toUserResponse
import example.com.model.User
import example.com.model.Users
import example.com.model.Users.select
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserDao {
    suspend fun findAll(): List<UserResponse> = dbQuery {
        Users.selectAll().map {
            User(
                idUser = it[Users.idUser],
                name = it[Users.name],
                email = it[Users.email],
                password = it[Users.password],
                date = it[Users.date],
                image = it[Users.image]
            ).toUserResponse()
        }
    }

    suspend fun findById(idUser: Int): User? = dbQuery {
        Users.selectAll().where { Users.idUser eq idUser }.map {
            User(
                idUser = it[Users.idUser],
                name = it[Users.name],
                email = it[Users.email],
                password = it[Users.password],
                date = it[Users.date],
                image = it[Users.image]
            )
        }.singleOrNull()
    }

    suspend fun findByEmail(email: String): User? = dbQuery {
        Users.selectAll().where { Users.email eq email }.map {
            User(
                idUser = it[Users.idUser],
                name = it[Users.name],
                email = it[Users.email],
                password = it[Users.password],
                date = it[Users.date],
                image = it[Users.image]
            )
        }.singleOrNull()
    }

    suspend fun findByEmailAndPassword(email: String, password: String): User? = dbQuery {
        Users.selectAll().where { (Users.email eq email) and (Users.password eq password) }.map {
            User(
                idUser = it[Users.idUser],
                name = it[Users.name],
                email = it[Users.email],
                password = it[Users.password],
                date = it[Users.date],
                image = it[Users.image]
            )
        }.singleOrNull()
    }

    suspend fun getUserIdByEmailAndPassword(email: String, password: String): Int? = dbQuery {
        Users.selectAll().where { (Users.email eq email) and (Users.password eq password) }
            .map { it[Users.idUser] }
            .singleOrNull()
    }

    suspend fun save(user: User): User = dbQuery {
        val insertStatement = Users.insert {
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password
            it[date] = user.date
            it[image] = user.image
        }
        insertStatement.resultedValues?.singleOrNull()?.let {
            User(
                idUser = it[Users.idUser],
                name = it[Users.name],
                email = it[Users.email],
                password = it[Users.password],
                date = it[Users.date],
                image = it[Users.image]
            )
        } ?: user
    }

    suspend fun update(user: User): Boolean = dbQuery {
        Users.update({ Users.idUser eq user.idUser }) {
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password
            it[date] = user.date
        } > 0
    }

    suspend fun updateImage(idUser: Int, image: String): Boolean = dbQuery {
        Users.update({ Users.idUser eq idUser }) {
            it[Users.image] = image
        } > 0
    }

    suspend fun updatePassword(idUser: Int, password: String): Boolean = dbQuery {
        Users.update({ Users.idUser eq idUser }) {
            it[Users.password] = password
        } > 0
    }

    suspend fun delete(idUser: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.idUser eq idUser } > 0
    }
}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }