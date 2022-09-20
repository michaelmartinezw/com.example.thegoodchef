package com.example.thegoodchef.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thegoodchef.databinding.ContenedorRecetasrandomBinding
import com.example.thegoodchef.models.FoodRecipe
import com.example.thegoodchef.models.Result
import com.example.thegoodchef.ui.DetalleActivity
import com.example.thegoodchef.utilidades.RecipesDiffUtil

/**
 * Created by animsh on 2/27/2021.
 */
class RecetasRandomAdapter(
    private var activity: Activity
) : RecyclerView.Adapter<RecetasRandomAdapter.RecipeViewHolder>() {

    private var recipes = emptyList<Result>()

    class RecipeViewHolder(private val binding: ContenedorRecetasrandomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecipeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ContenedorRecetasrandomBinding.inflate(layoutInflater, parent, false)
                return RecipeViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeViewHolder {
        return RecipeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
        holder.itemView.setOnClickListener {
            val intent = Intent(activity, DetalleActivity::class.java)
            intent.putExtra("result", currentRecipe)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: FoodRecipe) {
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}