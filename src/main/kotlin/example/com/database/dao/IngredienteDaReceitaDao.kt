package example.com.database.dao

import example.com.model.IngredienteDaReceita
import example.com.model.IngredienteDaReceitas
import org.jetbrains.exposed.sql.insert

class IngredienteDaReceitaDao {
    suspend fun addIngredienteToReceita(ingredienteDaReceita: IngredienteDaReceita) = dbQuery {
        IngredienteDaReceitas.insert {
            it[idReceita] = ingredienteDaReceita.idReceita
            it[idIngrediente] = ingredienteDaReceita.idIngrediente
        }
    }
}
