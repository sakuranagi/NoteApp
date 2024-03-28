package mbk.io.noteapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mbk.io.noteapp.data.database.NoteDatabase
import mbk.io.noteapp.data.repository.NoteRepository
import mbk.io.noteapp.domain.repositories.NoteRepositoryInt
import mbk.io.noteapp.domain.usecases.GetNoteUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteRepository(db: NoteDatabase): NoteRepositoryInt {
        return NoteRepository(db)
    }

    @Singleton
    @Provides
    fun provideUseCase(
        noteRepositoryInt: NoteRepositoryInt
    ):GetNoteUseCase{
        return GetNoteUseCase(noteRepositoryInt)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "database-name"
        ).build()
    }
}