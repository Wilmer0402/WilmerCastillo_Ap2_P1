package edu.ucne.wilmercastillo_ap2_p1.di

import androidx.databinding.tool.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.wilmercastillo_ap2_p1.data.local.Database.TareaDb


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideTareaDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            TareaDb::class.java,
            "Tarea.Db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideTareaDao(tareaDb: TareaDb) = tareaDb.TareaDao()
}

