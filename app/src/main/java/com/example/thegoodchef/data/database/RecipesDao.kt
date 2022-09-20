package com.example.thegoodchef.data.database

import androidx.room.*
import com.example.thegoodchef.data.database.entity.FavoritoEntity
import com.example.thegoodchef.data.database.entity.RecetaEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by animsh on 3/6/2021.
 */
@Dao
interface RecipesDao {

    // for offline recipes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecetaEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecetaEntity>>

    // for favorite recipes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavRecipe(favoritoEntity: FavoritoEntity)

    @Query("SELECT * FROM favorite_recipe_table ORDER BY id ASC")
    fun readFavRecipe(): Flow<List<FavoritoEntity>>

    @Delete
    suspend fun deleteFavRecipe(favoritoEntity: FavoritoEntity)

    @Query("DELETE FROM favorite_recipe_table")
    suspend fun deleteAllFavRecipes()
}