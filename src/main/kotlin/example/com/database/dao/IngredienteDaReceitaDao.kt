package example.com.database.dao

import example.com.model.Gostos
import example.com.model.IngredienteDaReceita
import example.com.model.IngredienteDaReceitas
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class IngredienteDaReceitaDao {
    suspend fun compareRecipeIngredientsWithUserPreferences(receitaId: Int, userId: Int): List<IngredienteDaReceita> =
        dbQuery {
            (IngredienteDaReceitas innerJoin Gostos)
                .select { (IngredienteDaReceitas.idReceita eq receitaId) and (Gostos.idUser eq userId) and (Gostos.escolha eq "gosta") }
                .map {
                    IngredienteDaReceita(
                        id = it[IngredienteDaReceitas.id],
                        idReceita = it[IngredienteDaReceitas.idReceita],
                        idIngrediente = it[IngredienteDaReceitas.idIngrediente]
                    )
                }
        }

    suspend fun addIngredienteToReceita(ingredienteDaReceita: IngredienteDaReceita) = dbQuery {
        IngredienteDaReceitas.insert {
            it[idReceita] = ingredienteDaReceita.idReceita
            it[idIngrediente] = ingredienteDaReceita.idIngrediente
        }
    }
}
