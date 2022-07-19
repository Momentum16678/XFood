package com.example.xfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xfood.api.RetrofitInstance
import com.example.xfood.db.MealDatabase
import com.example.xfood.model.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
): ViewModel() {

    private var categoryItemLiveData = MutableLiveData<List<Category>>()
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<PopularCategoryMeals>>()
    private var favoriteMealLiveData = mealDatabase.mealDao().getAllMeals()
    private var bottomSheetMealLiveData = MutableLiveData<Meal>()


    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal

                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
               Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<PopularCategoryList>{
            override fun onResponse(call: Call<PopularCategoryList>, response: Response<PopularCategoryList>) {
              if(response.body() != null){
                  popularItemsLiveData.value = response.body()!!.meals
              }
            }

            override fun onFailure(call: Call<PopularCategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategoryMeal().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    categoryItemLiveData.value = response.body()!!.categories
                }
                //or response.body?.let { it->
                //  categoryItemLiveDate.postValue(it)
                // }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getBottomMealById(Id: String){
        RetrofitInstance.api.getMealDetails(Id).enqueue(object : Callback<MealList>{

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    val meal = response.body()?.meals?.first()
                    meal?.let {
                    bottomSheetMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }
        })
    }


    fun deleteMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)
        }
    }

    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsertMeal(meal)
        }
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemLiveData():LiveData<List<PopularCategoryMeals>>{
        return popularItemsLiveData
    }

    fun observeCategoriesItemLiveData():LiveData<List<Category>>{
        return categoryItemLiveData
    }

    fun observeFavoriteMealLiveData():LiveData<List<Meal>>{
        return favoriteMealLiveData
    }

    fun observeBottomMealLiveData():LiveData<Meal>{
        return bottomSheetMealLiveData
    }
}