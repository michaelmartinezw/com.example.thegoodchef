package com.example.thegoodchef.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.thegoodchef.data.database.entity.FavoritoEntity
import com.example.thegoodchef.data.database.entity.RecetaEntity


@Database(
    entities = [RecetaEntity::class, FavoritoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipesDao

}