package com.example.thegoodchef.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thegoodchef.models.Result
import com.example.thegoodchef.utilidades.Constants.Companion.FAVORITE_RECIPE_TABLE

/**
 * Created by animsh on 4/30/2021.
 */
@Entity(tableName = FAVORITE_RECIPE_TABLE)
class FavoritoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)