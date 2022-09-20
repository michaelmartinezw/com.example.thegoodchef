package com.example.thegoodchef.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thegoodchef.databinding.PasosBinding
import com.example.thegoodchef.models.Step
import com.example.thegoodchef.utilidades.RecipesDiffUtil
import kotlinx.android.parcel.RawValue

/**
 * Created by animsh on 2/27/2021.
 */
class PasosAdapter : RecyclerView.Adapter<PasosAdapter.StepsViewHolder>() {

    private var steps = emptyList<Step>()

    class StepsViewHolder(private val binding: PasosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Step) {
            binding.step = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): StepsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PasosBinding.inflate(layoutInflater, parent, false)
                return StepsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StepsViewHolder {
        return StepsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        val current = steps[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    fun setData(newData: @RawValue List<Step>) {
        val recipesDiffUtil = RecipesDiffUtil(steps, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        steps = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

}