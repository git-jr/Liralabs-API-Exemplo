package example.com.repository

import example.com.database.dao.DietaDao
import example.com.database.dao.IngredienteDao
import example.com.database.dao.dbQuery
import example.com.dto.IngredienteResponse
import example.com.model.Dieta
import example.com.model.Dietas
import example.com.model.Ingrediente
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class DietaRepository(
    private val dao: DietaDao = DietaDao()
) {
    suspend fun getUserPreferences(id: Int, data: Int): List<Int> = dao.getUserPreferences(id, data)

    suspend fun getUserDiet(userId: Int) = dao.getUserDiet(userId)

    suspend fun addDiet(dieta: List<Dieta>) = dao.addDiet(dieta)
}