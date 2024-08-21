package example.com.repository

import example.com.database.dao.IngredienteDao
import example.com.dto.IngredienteResponse
import example.com.model.Ingrediente

class IngredienteRepository(
    private val dao: IngredienteDao = IngredienteDao()
) {
    suspend fun getUserIngredientsByPreference(userId: Int, escolha: String): List<IngredienteResponse> {
        return dao.findUserIngredientsByPreference(userId, escolha)
    }

    suspend fun saveAllIngredients(ingredientes: List<Ingrediente>) {
        dao.saveAll(ingredientes)
    }
}
