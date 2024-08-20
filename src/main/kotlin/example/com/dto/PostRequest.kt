package example.com.dto

import example.com.model.Post

data class PostRequest(
    val id: String,
    val user: String,
    val message: String,
    val img: String,
    var likes: Int
) {
    fun toPost() = Post(
        id = id,
        user = user,
        message = message,
        img = img,
        likes = likes
    )
}