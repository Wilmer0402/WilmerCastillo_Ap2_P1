package edu.ucne.wilmercastillo_ap2_p1.data.local.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.wilmercastillo_ap2_p1.data.local.Entity.TareaEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface  TareaDao{
    @Upsert()
    suspend fun save(tareas: TareaEntity)
    @Query(
        """
    SELECT *
    FROM Tareas
    WHERE  tareaId=:id
    LIMIT 1
    """
    )
    suspend fun find(id : Int): TareaEntity?
    @Delete
    suspend fun delete(tarea: TareaEntity)
    @Query("SELECT * FROM Tareas")
    fun getAll(): Flow<List<TareaEntity>>
}


