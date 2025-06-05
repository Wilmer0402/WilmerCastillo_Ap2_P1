package edu.ucne.wilmercastillo_ap2_p1.data.repository

import kotlinx.coroutines.flow.Flow
import edu.ucne.wilmercastillo_ap2_p1.data.local.Dao.TareaDao
import edu.ucne.wilmercastillo_ap2_p1.data.local.Entity.TareaEntity
import javax.inject.Inject


class TareaRepository @Inject constructor(
    private val dao: TareaDao
){
    suspend fun save(tarea: TareaEntity) = dao.save(tarea)

    suspend fun find(id: Int): TareaEntity? = dao.find(id)

    suspend fun delete(tarea: TareaEntity) = dao.delete(tarea)

    fun getAll(): Flow<List<TareaEntity>> = dao.getAll()

}
