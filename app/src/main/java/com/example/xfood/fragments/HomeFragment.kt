package com.example.xfood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.xfood.CategoryMainActivity
import com.example.xfood.MainActivity
import com.example.xfood.MealActivity
import com.example.xfood.adapters.CategoriesAdapter
import com.example.xfood.adapters.MostPopularAdapter
import com.example.xfood.databinding.FragmentHomeBinding
import com.example.xfood.model.PopularCategoryMeals
import com.example.xfood.model.Meal
import com.example.xfood.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularAdapter
    private lateinit var categoryItemAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.example.xfood.fragments.idMeal"
        const val MEAL_NAME = "com.example.xfood.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.xfood.fragments.thumbMeal"
        const val CATEGORY_NAME = "package com.example.xfood.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = (activity as MainActivity).viewModel
        popularItemAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observePopularItems()
        popularItemRecyclerView()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        homeMvvm.getCategories()
        observeCategoriesMeal()

        onCategoryClick()

    }

    //second categories card
    private fun onCategoryClick() {
        categoryItemAdapter.onItemClick = { category->
            val intent = Intent(activity, CategoryMainActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoryItemAdapter = CategoriesAdapter()
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL,false)
            adapter = categoryItemAdapter
        }
    }

    private fun observeCategoriesMeal() {
        homeMvvm.observeCategoriesItemLiveData().observe(viewLifecycleOwner) { categories->
                   categoryItemAdapter.setCategoriesMeal(categories)
              }
        }


    //RandomMealCard
    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner) {
            Glide.with(this@HomeFragment)
                .load(it.strMealThumb)
                .into(binding.ivRandomMeal)
            this.randomMeal = it
        }
    }

    //PopularItemCard
    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,it.idMeal)
            intent.putExtra(MEAL_NAME,it.strMeal)
            intent.putExtra(MEAL_THUMB,it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun popularItemRecyclerView() {
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter
        }
    }

    private fun observePopularItems() {
        homeMvvm.observePopularItemLiveData().observe(viewLifecycleOwner){
            popularItemAdapter.setMeal(mealsListPopular = it as ArrayList<PopularCategoryMeals>)
        }
    }
}
