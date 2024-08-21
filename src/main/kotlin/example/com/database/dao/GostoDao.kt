package example.com.database.dao

import example.com.model.*
import org.jetbrains.exposed.sql.*

class GostosDao {
    suspend fun getBestDietForUser(userId: Int): Receita? = dbQuery {
        (Receitas innerJoin IngredienteDaReceitas innerJoin Gostos)
            .selectAll().where { (Gostos.idUser eq userId) and (Gostos.escolha eq "gosta") }
            .groupBy(Receitas.idReceita)
            .orderBy(Count(Receitas.idReceita), SortOrder.DESC)
            .limit(1)
            .map {
                Receita(
                    idReceita = it[Receitas.idReceita],
                    imagem = it[Receitas.imagem],
                    nome = it[Receitas.nome],
                    tipo = it[Receitas.tipo],
                    url = it[Receitas.url]
                )
            }.firstOrNull()
    }

    suspend fun setUserPreference(gosto: Gosto) = dbQuery {
        Gostos.insert {
            it[idUser] = gosto.idUser
            it[idIngrediente] = gosto.idIngrediente
            it[escolha] = gosto.escolha
        }
    }
}

