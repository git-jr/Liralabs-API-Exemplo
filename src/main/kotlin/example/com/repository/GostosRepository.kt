package example.com.repository

import example.com.database.dao.GostosDao
import example.com.model.Gosto

class GostosRepository(
    private val dao: GostosDao = GostosDao()
) {
    suspend fun getUserPreferences(id: Int): List<Gosto> = dao.getUserPreferences(id)

    suspend fun setUserPreference(lista: List<Gosto>) = dao.setUserPreference(lista)

}