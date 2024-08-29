package example.com.repository

import example.com.database.dao.IngredienteDao
import example.com.dto.IngredienteResponse
import example.com.model.Ingrediente

class IngredienteRepository(
    private val dao: IngredienteDao = IngredienteDao()
) {
    suspend fun getUserIngredients(): List<Ingrediente> = dao.getIngredients()

    suspend fun saveAllIngredients(ingredientes: List<Ingrediente>) {
        dao.addIngredients(ingredientes)
    }
}