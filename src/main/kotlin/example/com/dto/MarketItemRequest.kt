package example.com.dto

import example.com.model.MarketItem
import kotlinx.serialization.Serializable

@Serializable
data class MarketItemRequest(
    val id: Long = 0,
    val user: String,
    val img: String,
    val price: String,
    val title: String,
    val description: String
) {
    fun toMarketItem() = MarketItem(
        id = id,
        user = user,
        img = img,
        price = price,
        title = title,
        description = description
    )
}
