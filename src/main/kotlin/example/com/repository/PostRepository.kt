package example.com.repository

import example.com.database.dao.PostDao
import example.com.model.Post

class PostRepository(
    private val dao: PostDao = PostDao()
) {
    suspend fun getAll() = dao.findAll()

    suspend fun getById(id: String) = dao.findById(id)

    suspend fun save(post: Post) = dao.save(post)

    suspend fun saveAll(posts: List<Post>) = dao.saveAll(posts)

    suspend fun delete(id: String) = dao.delete(id)
}