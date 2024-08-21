package example.com.database.dao

import example.com.model.Dieta
import example.com.model.Dietas
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class DietaDao {
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

    suspend fun addDiet(dieta: Dieta) = dbQuery {
        Dietas.insert {
            it[idUser] = dieta.idUser
            it[idReceita] = dieta.idReceita
        }
    }
}
