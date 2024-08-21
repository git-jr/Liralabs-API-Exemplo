package example.com.repository

import example.com.database.dao.ReceitaDao
import example.com.dto.ReceitaResponse
import example.com.model.Receita


class ReceitaRepository(
    private val dao: ReceitaDao = ReceitaDao()
) {
    suspend fun getAllReceitas(): List<ReceitaResponse> {
        return dao.findAll()
    }

    suspend fun saveReceitas(receitas: List<Receita>) {
        dao.saveAll(receitas)
    }
}

