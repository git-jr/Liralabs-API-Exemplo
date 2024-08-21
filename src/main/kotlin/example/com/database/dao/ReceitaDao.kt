package example.com.database.dao

import example.com.dto.ReceitaResponse
import example.com.dto.toReceitaResponse
import example.com.model.Receita
import example.com.model.Receitas
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll

class ReceitaDao {
    suspend fun findAll(): List<ReceitaResponse> = dbQuery {
        Receitas.selectAll().map {
            Receita(
                idReceita = it[Receitas.idReceita],
                imagem = it[Receitas.imagem],
                nome = it[Receitas.nome],
                tipo = it[Receitas.tipo],
                url = it[Receitas.url]
            ).toReceitaResponse()
        }
    }

    suspend fun saveAll(receitas: List<Receita>) = dbQuery {
        Receitas.batchInsert(receitas) { receita ->
            this[Receitas.imagem] = receita.imagem
            this[Receitas.nome] = receita.nome
            this[Receitas.tipo] = receita.tipo
            this[Receitas.url] = receita.url
        }
    }
}
