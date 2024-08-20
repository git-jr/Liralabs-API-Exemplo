package example.com.database.dao

import example.com.model.MarketItem
import example.com.model.MarketItems
import example.com.model.User
import example.com.model.Users
import example.com.model.Users.email
import example.com.model.Users.name
import example.com.model.Users.password
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class MarketItemDao {
    suspend fun findAll(): List<MarketItem> = dbQuery {
        MarketItems.selectAll().map {
            MarketItem(
                id = it[MarketItems.id],
                user = it[MarketItems.user],
                img = it[MarketItems.img],
                price = it[MarketItems.price],
                title = it[MarketItems.title],
                description = it[MarketItems.description]
            )
        }
    }

    suspend fun save(marketItem: MarketItem) =
        dbQuery {
            val insertStatement = MarketItems.insert {
                it[id] = marketItem.id
                it[user] = marketItem.user
                it[img] = marketItem.img
                it[price] = marketItem.price
                it[title] = marketItem.title
                it[description] = marketItem.description
            }
            // Return the user that was inserted
            insertStatement.resultedValues?.singleOrNull()?.let {
                MarketItem(
                    id = it[MarketItems.id],
                    user = it[MarketItems.user],
                    img = it[MarketItems.img],
                    price = it[MarketItems.price],
                    title = it[MarketItems.title],
                    description = it[MarketItems.description]
                )
            }
        }

    suspend fun delete(id: Long): Boolean {
        return dbQuery {
            MarketItems.deleteWhere { MarketItems.id eq id } > 0
        }
    }

    suspend fun findById(id: Long): MarketItem {
        return dbQuery {
            MarketItems.selectAll().where { MarketItems.id eq id }.map {
                MarketItem(
                    id = it[MarketItems.id],
                    user = it[MarketItems.user],
                    img = it[MarketItems.img],
                    price = it[MarketItems.price],
                    title = it[MarketItems.title],
                    description = it[MarketItems.description]
                )
            }.first()
        }
    }

    suspend fun saveAll(marketItems: List<MarketItem>): Boolean {
        return dbQuery {
            MarketItems.batchInsert(marketItems) { marketItem ->
                this[MarketItems.id] = marketItem.id
                this[MarketItems.user] = marketItem.user
                this[MarketItems.img] = marketItem.img
                this[MarketItems.price] = marketItem.price
                this[MarketItems.title] = marketItem.title
                this[MarketItems.description] = marketItem.description
            }.count() == marketItems.size
        }
    }

}
