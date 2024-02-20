package com.pe.mascotapp.components

import android.app.Application
import androidx.room.Room
import com.pe.mascotapp.data.data_source.PetsDatabase
import com.pe.mascotapp.data.repository.ReminderRepository
import com.pe.mascotapp.domain.repository.ReminderRepositoryImpl
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): PetsDatabase {
        return Room.databaseBuilder(
            app,
            PetsDatabase::class.java,
            PetsDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: PetsDatabase): ReminderRepository {
        return ReminderRepositoryImpl(db.reminderDao)
    }
}