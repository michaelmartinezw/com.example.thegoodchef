package com.example.thegoodchef.adapters

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thegoodchef.R
import com.example.thegoodchef.data.database.entity.FavoritoEntity
import com.example.thegoodchef.databinding.ContendorFavoritosBinding
import com.example.thegoodchef.databinding.PrincipalBinding

import com.example.thegoodchef.ui.DetalleActivity
import com.example.thegoodchef.utilidades.RecipesDiffUtil
import com.example.thegoodchef.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar


/**
 * Created by animsh on 5/1/2021.
 */
class RecetasFavAdapter(
    private var activity: Activity,
    private val activityMainBinding: PrincipalBinding,
    private var mainViewModel: MainViewModel
) : RecyclerView.Adapter<RecetasFavAdapter.FavRecipeViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private lateinit var dActionMode: ActionMode
    private var selectedRecipes = arrayListOf<FavoritoEntity>()
    private var myHolders = arrayListOf<FavRecipeViewHolder>()
    private lateinit var rootView: View

    private var favRecipeList = emptyList<FavoritoEntity>()

    class FavRecipeViewHolder(val binding: ContendorFavoritosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritoEntity: FavoritoEntity) {
            binding.result = favoritoEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavRecipeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ContendorFavoritosBinding.inflate(layoutInflater, parent, false)
                return FavRecipeViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavRecipeViewHolder {
        return FavRecipeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavRecipeViewHolder, position: Int) {
        rootView = holder.itemView.rootView
        myHolders.add(holder)
        val current = favRecipeList[position]
        saveSelection(holder, current)
        holder.bind(current)
        holder.itemView.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, current)
            } else {
                val intent = Intent(activity, DetalleActivity::class.java)
                intent.putExtra("result", current.result)
                activity.startActivity(intent)
            }
        }
        holder.itemView.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                activity.startActionMode(this)
                activityMainBinding.appBar.layoutParams.height = 0
                applySelection(holder, current)
                true
            } else {
                applySelection(holder, current)
                true
            }

        }
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.menu_favorito, menu)
        dActionMode = actionMode!!
        when (activity.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                applyStatusColor(R.color.backgroundColorDark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                applyStatusColor(R.color.backgroundColorLight)
            }
        }
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_fav_recipe) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavRecipe(it)
            }
            showSnackBarMessage("${selectedRecipes.size} Recipe/s deleted!!")
            selectedRecipes.clear()
            multiSelection = false
            dActionMode.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        myHolders.forEach { holder ->
            setColorAsPerMode(holder)
        }
        multiSelection = false
        selectedRecipes.clear()
        activityMainBinding.appBar.layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    private fun saveSelection(holder: FavRecipeViewHolder, currentRecipe: FavoritoEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            changeRecipeStyle(holder, R.color.lightThemePrimary, R.color.themePrimary)
        } else {
            setColorAsPerMode(holder)
        }
    }

    private fun applySelection(holder: FavRecipeViewHolder, currentRecipe: FavoritoEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            setColorAsPerMode(holder)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.lightThemePrimary, R.color.themePrimary)
            applyActionModeTitle()
        }
    }

    /* private fun setupAppBar(height: Int, marginTop: Int) {
         activityMainBinding.appBar.layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
         val margin = activityMainBinding.appBar.layoutParams as ViewGroup.MarginLayoutParams
         margin.topMargin = marginTop
     }*/

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> dActionMode.finish()
            1 -> dActionMode.title = "${selectedRecipes.size} item selected"
            else -> dActionMode.title = "${selectedRecipes.size} items selected"
        }
    }

    private fun changeRecipeStyle(
        holder: FavRecipeViewHolder,
        backGroundColor: Int,
        strokeColor: Int
    ) {
        holder.binding.layoutContainerChild.setBackgroundColor(
            ContextCompat.getColor(
                activity,
                backGroundColor
            )
        )

        holder.binding.layoutContainer.strokeWidth = 1
        holder.binding.layoutContainer.strokeColor = ContextCompat.getColor(
            activity,
            strokeColor
        )
    }

    private fun setColorAsPerMode(holder: FavRecipeViewHolder) {
        when (activity.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                changeRecipeStyle(
                    holder,
                    R.color.backgroundLayoutColorDark,
                    R.color.backgroundLayoutColorDark
                )
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                changeRecipeStyle(
                    holder,
                    R.color.backgroundLayoutColorLight,
                    R.color.backgroundLayoutColorLight
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return favRecipeList.size
    }

    fun setData(newFavRecipeList: List<FavoritoEntity>) {
        val favRecipesDiffUtils = RecipesDiffUtil(favRecipeList, newFavRecipeList)
        val diffUtilResult = DiffUtil.calculateDiff(favRecipesDiffUtils)
        favRecipeList = newFavRecipeList
        diffUtilResult.dispatchUpdatesTo(this)

    }

    private fun applyStatusColor(color: Int) {
        activity.window.statusBarColor = ContextCompat.getColor(activity, color)
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    fun removeContextualActionMode() {
        if (this::dActionMode.isInitialized) {
            dActionMode.finish()
        }
    }
}