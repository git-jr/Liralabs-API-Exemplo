package example.com.database.dao

import example.com.model.Gosto
import example.com.model.Gostos
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class GostosDao {
    suspend fun getUserPreferences(id: Int): List<Gosto> = dbQuery {
        Gostos.selectAll().where { Gostos.idGostos eq id }
            .map {
                Gosto(
                    idGostos = it[Gostos.idGostos],
                    idUser = it[Gostos.idUser],
                    idIngrediente = it[Gostos.idIngrediente],
                )
            }
    }

    suspend fun setUserPreference(lista: List<Gosto>) {
        for (gosto in lista) {
            dbQuery {
                Gostos.insert {
                    it[idGostos] = gosto.idGostos
                    it[idUser] = gosto.idUser
                    it[idIngrediente] = gosto.idIngrediente
                }
            }
        }
    }
}

