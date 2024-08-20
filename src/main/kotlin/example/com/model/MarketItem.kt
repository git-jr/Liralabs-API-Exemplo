package example.com.model

import org.jetbrains.exposed.sql.Table

data class MarketItem(
    val id: Long = 0,
    val user: String,
    val img: String,
    val price: String,
    val title: String,
    val description: String
)

object MarketItems : Table() {
    val id = long("id").autoIncrement()
    val user = text("user")
    val img = text("img")
    val price = text("price")
    val title = text("title")
    val description = text("description")

    override val primaryKey = PrimaryKey(id)
}
