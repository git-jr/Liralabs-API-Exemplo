package example.com.database.dao

import example.com.model.User
import example.com.model.Users
import example.com.model.Users.select
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserDao {
    suspend fun findAll(): List<User> = dbQuery {
        Users.selectAll().map {
            User(
                id = it[Users.id],
                name = it[Users.name],
                email = it[Users.email],
                password = it[Users.password],
                img = it[Users.img]
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
                    email = it[Users.email],
                    password = it[Users.password],
                    img = it[Users.img]
                )
            }
        }

    suspend fun update(user: User): Boolean {
        return dbQuery {
            Users.update({ Users.id eq user.id }) {
                it[name] = user.name
                it[email] = user.email
                it[password] = user.password
            } > 0
        }
    }

    suspend fun findByEmailAndPassword(email: String, password: String): User {
        return dbQuery {
            Users.selectAll().where { Users.email.eq(email) and Users.password.eq(password) }
                .map {
                    User(
                        id = it[Users.id],
                        name = it[Users.name],
                        email = it[Users.email],
                        password = it[Users.password],
                        img = it[Users.img]
                    )
                }.first()
        }
    }

    suspend fun findById(id: String): User? = dbQuery {
        Users.selectAll().where { Users.id eq id }
            .map {
                User(
                    id = it[Users.id],
                    name = it[Users.name],
                    email = it[Users.email],
                    password = it[Users.password],
                    img = it[Users.img]
                )
            }.firstOrNull()
    }

    suspend fun saveImage(id: String, img: String): Boolean {
        return dbQuery {
            Users.update({ Users.id eq id }) {
                it[Users.img] = img
            } > 0
        }
    }

    suspend fun delete(id: String): Boolean {
        return dbQuery {
            Users.deleteWhere { Users.id eq id } > 0
        }
    }

    suspend fun saveAll(users: List<User>) = dbQuery {
        Users.batchInsert(users) { user ->
            this[Users.id] = user.id
            this[Users.name] = user.name
            this[Users.email] = user.email
            this[Users.password] = user.password
        }
    }

}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }