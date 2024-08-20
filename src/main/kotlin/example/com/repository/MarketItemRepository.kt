package example.com.repository

import example.com.database.dao.MarketItemDao
import example.com.database.dao.UserDao
import example.com.model.MarketItem
import example.com.model.User

class MarketItemRepository(
    private val dao: MarketItemDao = MarketItemDao()
) {

    suspend fun getAll() = dao.findAll()

    suspend fun getById(id: Long) = dao.findById(id)

    suspend fun save(marketItem: MarketItem) = dao.save(marketItem)

    suspend fun saveAll(marketItems: List<MarketItem>) = dao.saveAll(marketItems)

    suspend fun delete(id: Long) = dao.delete(id)
}