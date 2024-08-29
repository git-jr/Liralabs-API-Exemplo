package example.com.database.dao

import example.com.model.Dieta
import example.com.model.Dietas
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class DietaDao {
    suspend fun getUserPreferences(id: Int, data: Int): List<Int> = dbQuery {
        Dietas.selectAll().where { Dietas.idUser eq id }
            .map {
                it[Dietas.idReceita]
            }
    }

    suspend fun getUserDiet(userId: Int): List<Dieta> = dbQuery {
        Dietas.selectAll().where { Dietas.idUser eq userId }
            .map {
                Dieta(
                    idDieta = it[Dietas.idDieta],
                    idUser = it[Dietas.idUser],
                    idReceita = it[Dietas.idReceita]
                )
            }
    }

    suspend fun addDiet(dietas: List<Dieta>) = dbQuery {
        Dietas.batchInsert(dietas) { dieta ->
            this[Dietas.idUser] = dieta.idUser
            this[Dietas.idReceita] = dieta.idReceita
        }
    }
}
