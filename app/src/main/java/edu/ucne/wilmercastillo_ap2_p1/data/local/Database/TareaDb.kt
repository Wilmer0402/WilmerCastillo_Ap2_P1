package edu.ucne.wilmercastillo_ap2_p1.data.local.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.wilmercastillo_ap2_p1.data.local.Dao.TareaDao
import edu.ucne.wilmercastillo_ap2_p1.data.local.Entity.TareaEntity


@Database(
    entities = [
        TareaEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class TareaDb : RoomDatabase(){
    abstract fun TareaDao(): TareaDao
}


