package com.example.thegoodchef.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thegoodchef.databinding.IngredientesBinding
import com.example.thegoodchef.models.ExtendedIngredient
import com.example.thegoodchef.utilidades.RecipesDiffUtil
import kotlinx.parcelize.RawValue


class IngredientesAdapter : RecyclerView.Adapter<IngredientesAdapter.IngredientsViewHolder>() {

    private var ingredients = emptyList<ExtendedIngredient>()

    class IngredientsViewHolder(val binding: IngredientesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: ExtendedIngredient) {
            binding.ingredient = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): IngredientsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngredientesBinding.inflate(layoutInflater, parent, false)
                return IngredientsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientsViewHolder {
        return IngredientsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val current = ingredients[position]
        holder.bind(current)
        holder.binding.name.isSelected = true
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    fun setData(newData: @RawValue List<ExtendedIngredient?>?) {
        val recipesDiffUtil = RecipesDiffUtil(ingredients, newData!!)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        ingredients = (newData as List<ExtendedIngredient>?)!!
        diffUtilResult.dispatchUpdatesTo(this)
    }

}
