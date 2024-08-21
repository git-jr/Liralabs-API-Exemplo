package example.com.model

import org.jetbrains.exposed.sql.Table

data class Status(
    val idStatus: Int,
    val status: String
)

object Statuses : Table() {
    val idStatus = integer("idStatus").autoIncrement()
    val status = text("status")

    override val primaryKey = PrimaryKey(idStatus)
}


