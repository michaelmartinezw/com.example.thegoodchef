package com.example.thegoodchef.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thegoodchef.adapters.RecetasFavAdapter
import com.example.thegoodchef.data.database.entity.FavoritoEntity
import com.example.thegoodchef.data.database.entity.RecetaEntity
import com.example.thegoodchef.models.FoodRecipe
import com.example.thegoodchef.utilidades.NetworkResult
import com.makeramen.roundedimageview.RoundedImageView

/**
 * Created by animsh on 3/10/2021.
 */
class RecipeBindingAdapter {

    companion object {

        @BindingAdapter("android:readApiResponse", "android:readDatabase", requireAll = true)
        @JvmStatic
        fun setError(
            textView: TextView,
            apiResponse: NetworkResult<FoodRecipe?>?,
            database: List<RecetaEntity?>?
        ) {
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else if (apiResponse is NetworkResult.Loading) {
                textView.visibility = View.INVISIBLE
            } else if (apiResponse is NetworkResult.Success) {
                textView.visibility = View.INVISIBLE
            }

        }

        @BindingAdapter("android:viewVisibility", "android:setData", requireAll = false)
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            favoritesEntity: List<FavoritoEntity>?,
            adapter: RecetasFavAdapter?
        ) {
            if (favoritesEntity.isNullOrEmpty()) {
                when (view) {
                    is RoundedImageView -> {
                        view.visibility = View.VISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.INVISIBLE
                    }
                }
            } else {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        adapter?.setData(favoritesEntity)
                    }
                }
            }
        }
    }
}