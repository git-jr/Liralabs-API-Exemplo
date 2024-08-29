package example.com.repository

import example.com.database.dao.IngredienteDaReceitaDao
import example.com.model.IngredienteDaReceita

class IngredienteDaReceitaRepository(
    private val dao: IngredienteDaReceitaDao = IngredienteDaReceitaDao()
) {
    suspend fun addIngredienteToReceita(ingredienteDaReceita: IngredienteDaReceita) =
        dao.addIngredienteToReceita(ingredienteDaReceita)
}