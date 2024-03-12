package com.pe.mascotapp.components

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.work.WorkerParameters
import com.pe.mascotapp.data.data_source.PetsDatabase
import com.pe.mascotapp.data.repository.PetRepository
import com.pe.mascotapp.data.repository.ReminderPetJoinRepository
import com.pe.mascotapp.data.repository.ReminderRepository
import com.pe.mascotapp.domain.repository.PetRepositoryImpl
import com.pe.mascotapp.domain.repository.ReminderPetJoinRepositoryImpl
import com.pe.mascotapp.domain.repository.ReminderRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): PetsDatabase {
        return Room.databaseBuilder(
            app,
            PetsDatabase::class.java,
            PetsDatabase.DATABASE_NAME
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: PetsDatabase): ReminderRepository {
        return ReminderRepositoryImpl(db.reminderDao)
    }

    @Provides
    @Singleton
    fun provideRepositoryReminderPetJoin(db: PetsDatabase): ReminderPetJoinRepository {
        return ReminderPetJoinRepositoryImpl(db.reminderPetJoinDao)
    }

    @Provides
    @Singleton
    fun provideRepositoryPet(db: PetsDatabase): PetRepository {
        return PetRepositoryImpl(db.petDao)
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return Application().applicationContext
    }

}