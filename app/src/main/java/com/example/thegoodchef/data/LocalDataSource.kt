package com.example.thegoodchef.data

import com.example.thegoodchef.data.database.RecipesDao
import com.example.thegoodchef.data.database.entity.FavoritoEntity
import com.example.thegoodchef.data.database.entity.RecetaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {
    fun readDatabase(): Flow<List<RecetaEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipe(recetaEntity: RecetaEntity) {
        recipesDao.insertRecipes(recetaEntity)
    }

    fun readFavRecipe(): Flow<List<FavoritoEntity>> {
        return recipesDao.readFavRecipe()
    }

    suspend fun insertFavRecipe(favoritoEntity: FavoritoEntity) {
        recipesDao.insertFavRecipe(favoritoEntity)
    }

    suspend fun deleteFavRecipe(favoritoEntity: FavoritoEntity) {
        recipesDao.deleteFavRecipe(favoritoEntity)
    }

    suspend fun deleteAllFavRecipes() {
        recipesDao.deleteAllFavRecipes()
    }
}