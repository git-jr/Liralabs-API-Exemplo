package example.com.repository

import example.com.database.dao.PetDao
import example.com.dto.PetResponse
import example.com.model.Pet

class PetRepository(
    private val dao: PetDao = PetDao()
) {
    suspend fun getAllPets(): List<PetResponse> = dao.findAll()

    suspend fun getPetById(id: Long): Pet? = dao.findById(id)

    suspend fun savePet(pet: Pet): Pet = dao.save(pet)

    suspend fun saveAllPets(pets: List<Pet>) = dao.saveAll(pets)

    suspend fun updatePet(pet: Pet): Boolean {
        return dao.update(pet)
    }

    suspend fun deletePet(id: Long): Boolean {
        return dao.delete(id)
    }
}
