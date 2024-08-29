package example.com.database.dao

import example.com.model.Ingrediente
import example.com.model.Ingredientes
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll

class IngredienteDao {
    suspend fun addIngredients(ingredientes: List<Ingrediente>) = dbQuery {
        Ingredientes.batchInsert(ingredientes) { ingrediente ->
            this[Ingredientes.nomeIngrediente] = ingrediente.nomeIngrediente
        }
    }

    suspend fun getIngredients(): List<Ingrediente> = dbQuery {
        Ingredientes.selectAll()
            .map {
                Ingrediente(
                    idIngrediente = it[Ingredientes.idIngrediente],
                    nomeIngrediente = it[Ingredientes.nomeIngrediente]
                )
            }
    }
}
