package example.com.repository

import example.com.database.dao.PetshopDao
import example.com.dto.PetshopResponse
import example.com.model.Petshop

class PetshopRepository(
    private val dao: PetshopDao = PetshopDao()
) {
    suspend fun getAllPetshops(): List<PetshopResponse> = dao.findAll()

    suspend fun getPetshopByCnpj(cnpj: String): Petshop? = dao.findByCnpj(cnpj)

    suspend fun savePetshop(petshop: Petshop): Petshop = dao.save(petshop)

    suspend fun saveAllPetshops(petshops: List<Petshop>) = dao.saveAll(petshops)

    suspend fun deletePetshop(uid: Int): Boolean {
        return dao.delete(uid)
    }
}
