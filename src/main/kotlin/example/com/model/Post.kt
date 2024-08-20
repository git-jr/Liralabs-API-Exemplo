package example.com.model

import org.jetbrains.exposed.sql.Table

data class Post(
    val id: String,
    val user: String,
    val message: String,
    val img: String,
    var likes: Int
)

object Posts : Table() {
    val id = text("id")
    val user = text("user")
    val message = text("message")
    val img = text("img")
    val likes = integer("likes")

    override val primaryKey = PrimaryKey(id)
}