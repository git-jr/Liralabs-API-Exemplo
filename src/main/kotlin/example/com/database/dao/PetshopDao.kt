package example.com.database.dao

import example.com.dto.PetshopResponse
import example.com.dto.toPetshopResponse
import example.com.model.Petshop
import example.com.model.Petshops
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PetshopDao {
    suspend fun findAll(): List<PetshopResponse> = dbQuery {
        Petshops.selectAll().map {
            Petshop(
                uid = it[Petshops.uid],
                name = it[Petshops.name],
                imagem = it[Petshops.imagem],
                localizacao = it[Petshops.localizacao],
                descricao = it[Petshops.descricao],
                servicos = it[Petshops.servicos],
                cnpj = it[Petshops.cnpj]
            ).toPetshopResponse()
        }
    }

    suspend fun save(petshop: Petshop): Petshop = dbQuery {
        val insertStatement = Petshops.insert {
            it[name] = petshop.name
            it[imagem] = petshop.imagem
            it[localizacao] = petshop.localizacao
            it[descricao] = petshop.descricao
            it[servicos] = petshop.servicos
            it[cnpj] = petshop.cnpj
        }
        insertStatement.resultedValues?.singleOrNull()?.let {
            Petshop(
                uid = it[Petshops.uid],
                name = it[Petshops.name],
                imagem = it[Petshops.imagem],
                localizacao = it[Petshops.localizacao],
                descricao = it[Petshops.descricao],
                servicos = it[Petshops.servicos],
                cnpj = it[Petshops.cnpj]
            )
        } ?: petshop
    }

    suspend fun saveAll(petshops: List<Petshop>) = dbQuery {
        Petshops.batchInsert(petshops) { petshop ->
            this[Petshops.name] = petshop.name
            this[Petshops.imagem] = petshop.imagem
            this[Petshops.localizacao] = petshop.localizacao
            this[Petshops.descricao] = petshop.descricao
            this[Petshops.servicos] = petshop.servicos
            this[Petshops.cnpj] = petshop.cnpj
        }
    }

    suspend fun delete(uid: Int): Boolean {
        return dbQuery {
            Petshops.deleteWhere { Petshops.uid eq uid } > 0
        }
    }

    suspend fun findByCnpj(cnpj: String): Petshop? = dbQuery {
        Petshops.select { Petshops.cnpj eq cnpj }.map {
            Petshop(
                uid = it[Petshops.uid],
                name = it[Petshops.name],
                imagem = it[Petshops.imagem],
                localizacao = it[Petshops.localizacao],
                descricao = it[Petshops.descricao],
                servicos = it[Petshops.servicos],
                cnpj = it[Petshops.cnpj]
            )
        }.singleOrNull()
    }
}
