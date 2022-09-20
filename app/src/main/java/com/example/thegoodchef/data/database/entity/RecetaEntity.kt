package com.example.thegoodchef.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thegoodchef.models.FoodRecipe
import com.example.thegoodchef.utilidades.Constants.Companion.RECIPE_TABLE

/**
 * Created by animsh on 3/6/2021.
 */
@Entity(tableName = RECIPE_TABLE)
class RecetaEntity(
    val foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}