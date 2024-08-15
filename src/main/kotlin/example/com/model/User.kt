package example.com.model

import org.jetbrains.exposed.sql.Table
import java.util.*

data class User(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val password: String,
    val email: String
)

object Users : Table() {
    val id = uuid("id")
    val name = text("name")
    val password = varchar("password", 50)
    val email = varchar("email", 50)

    override val primaryKey = PrimaryKey(id)
}