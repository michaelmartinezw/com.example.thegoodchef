package com.example.thegoodchef.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.thegoodchef.R
import com.example.thegoodchef.databinding.PrincipalBinding
import com.example.thegoodchef.ui.fragments.favorite.FavRecipesFragment
import com.example.thegoodchef.ui.fragments.recipes.RecipesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: PrincipalBinding
    private val recipesFragment: Fragment = RecipesFragment()
    private val favRecipesFragment: Fragment by lazy {
        FavRecipesFragment(binding)
    }

    //    private val foodJokeFragment: Fragment = FoodJokeFragment()
    private val fragmentManager: FragmentManager = supportFragmentManager
    private var active = recipesFragment
    private var backStack: MutableList<Fragment> = Stack()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        binding = PrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            setupBottomNav()
            searchBtn.setOnClickListener {
                val intent = Intent(this@PrincipalActivity, BusquedaActivity::class.java)
                startActivity(intent)
            }

            settingBtn.setOnClickListener {
                val intent = Intent(this@PrincipalActivity, ConfiguracionActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.recipesFragment -> {
                    fragmentManager.beginTransaction().hide(active).show(recipesFragment).commit()
                    active = recipesFragment
                    if (backStack[backStack.lastIndex] != active)
                        backStack.add(active)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favRecipesFragment -> {
                    fragmentManager.beginTransaction().hide(active).show(favRecipesFragment)
                        .commit()
                    active = favRecipesFragment
                    if (backStack[backStack.lastIndex] != active)
                        backStack.add(active)
                    return@OnNavigationItemSelectedListener true
                }
                /* R.id.foodJokeFragment -> {
                     fragmentManager.beginTransaction().hide(active).show(foodJokeFragment).commit()
                     active = foodJokeFragment
                     if (backStack[backStack.lastIndex] != active)
                         backStack.add(active)
                     return@OnNavigationItemSelectedListener true
                 }*/
            }
            false
        }

    override fun onBackPressed() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
            fragmentManager.beginTransaction().hide(active).show(backStack[backStack.lastIndex])
                .commit()
            active = backStack[backStack.lastIndex]
            binding.bottomNavigationView.menu.getItem(active.tag!!.toInt()).isChecked = true
        } else {
            super.onBackPressed()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.bottomNavigationView.apply {
            setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        }
        binding.bottomNavigationView.menu.getItem(0).isChecked = true
    }

    private fun setupBottomNav() {
        backStack.add(recipesFragment)
        binding.bottomNavigationView.apply {
            setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        }
        if (fragmentManager.findFragmentByTag("1") == null || fragmentManager.findFragmentByTag(
                "0"
            ) == null
        ) {
            /* fragmentManager.beginTransaction()
                 .add(R.id.main_container, foodJokeFragment, "2")
                 .hide(foodJokeFragment).commit()*/
            fragmentManager.beginTransaction()
                .add(R.id.main_container, favRecipesFragment, "1")
                .hide(favRecipesFragment).commit()
            fragmentManager.beginTransaction()
                .add(R.id.main_container, recipesFragment, "0")
                .commit()
        }
    }
}