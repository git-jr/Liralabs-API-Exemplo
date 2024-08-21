package example.com.database.dao

import example.com.dto.PetResponse
import example.com.dto.UserResponse
import example.com.dto.toPetResponse
import example.com.dto.toUserResponse
import example.com.model.Pet
import example.com.model.Pets
import example.com.model.User
import example.com.model.Users
import example.com.model.Users.select
import io.ktor.http.cio.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PetDao {
    suspend fun findAll(): List<PetResponse> = dbQuery {
        Pets.selectAll().map {
            Pet(
                id = it[Pets.id],
                name = it[Pets.name],
                descricao = it[Pets.descricao],
                peso = it[Pets.peso],
                idade = it[Pets.idade],
                imagem = it[Pets.imagem]
            ).toPetResponse()
        }
    }

    suspend fun save(pet: Pet): Pet = dbQuery {
        val insertStatement = Pets.insert {
            it[name] = pet.name
            it[descricao] = pet.descricao
            it[peso] = pet.peso
            it[idade] = pet.idade
            it[imagem] = pet.imagem
        }
        insertStatement.resultedValues?.singleOrNull()?.let {
            Pet(
                id = it[Pets.id],
                name = it[Pets.name],
                descricao = it[Pets.descricao],
                peso = it[Pets.peso],
                idade = it[Pets.idade],
                imagem = it[Pets.imagem]
            )
        } ?: pet
    }

    suspend fun update(pet: Pet): Boolean = dbQuery {
        Pets.update({ Pets.id eq pet.id }) {
            it[name] = pet.name
            it[descricao] = pet.descricao
            it[peso] = pet.peso
            it[idade] = pet.idade
            it[imagem] = pet.imagem
        } > 0
    }

    suspend fun findById(id: Long): Pet? = dbQuery {
        Pets.select { Pets.id eq id }.map {
            Pet(
                id = it[Pets.id],
                name = it[Pets.name],
                descricao = it[Pets.descricao],
                peso = it[Pets.peso],
                idade = it[Pets.idade],
                imagem = it[Pets.imagem]
            )
        }.singleOrNull()
    }

    suspend fun delete(id: Long): Boolean = dbQuery {
        Pets.deleteWhere { Pets.id eq id } > 0
    }

    suspend fun saveAll(pets: List<Pet>) = dbQuery {
        Pets.batchInsert(pets) { pet ->
            this[Pets.name] = pet.name
            this[Pets.descricao] = pet.descricao
            this[Pets.peso] = pet.peso
            this[Pets.idade] = pet.idade
            this[Pets.imagem] = pet.imagem
        }
    }
}
