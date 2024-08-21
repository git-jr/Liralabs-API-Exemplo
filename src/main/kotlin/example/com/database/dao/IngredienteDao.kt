package example.com.database.dao

import example.com.dto.IngredienteResponse
import example.com.dto.toIngredienteResponse
import example.com.model.Gostos
import example.com.model.Ingrediente
import example.com.model.Ingredientes
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll

class IngredienteDao {
    suspend fun findUserIngredientsByPreference(userId: Int, escolha: String): List<IngredienteResponse> = dbQuery {
        (Ingredientes innerJoin Gostos)
            .selectAll().where { (Gostos.idUser eq userId) and (Gostos.escolha eq escolha) }
            .map {
                Ingrediente(
                    idIngrediente = it[Ingredientes.idIngrediente],
                    nomeIngrediente = it[Ingredientes.nomeIngrediente]
                )
            }.map { it.toIngredienteResponse() }
    }

    suspend fun saveAll(ingredientes: List<Ingrediente>) = dbQuery {
        Ingredientes.batchInsert(ingredientes) { ingrediente ->
            this[Ingredientes.nomeIngrediente] = ingrediente.nomeIngrediente
        }
    }
}
