package com.example.thegoodchef.di

import android.content.Context
import androidx.room.Room
import com.example.thegoodchef.data.database.RecipesDatabase
import com.example.thegoodchef.utilidades.Constants.Companion.RECIPE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        RECIPE_DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipeDao()
}