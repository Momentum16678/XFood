package com.example.xfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.xfood.adapters.CategoriesMealAdapter
import com.example.xfood.databinding.ActivityCategoryMainBinding
import com.example.xfood.fragments.HomeFragment
import com.example.xfood.viewModel.CategoryMealsViewModel

class CategoryMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMainBinding
    lateinit var categoryMvvm: CategoryMealsViewModel
    lateinit var categoriesMealAdapter: CategoriesMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMvvm = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        categoryMvvm.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMvvm.observeMealsByCategoryLiveData().observe(this){ mealsList ->
            binding.tvCategoryText.text = mealsList.size.toString()
            categoriesMealAdapter.setCategoryMealList(mealsList)
        }
    }

    private fun prepareRecyclerView() {
      categoriesMealAdapter = CategoriesMealAdapter()
      binding.rvMeals.apply {
          layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
          adapter = categoriesMealAdapter
      }
    }
}