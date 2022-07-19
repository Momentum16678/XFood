package com.example.xfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xfood.api.RetrofitInstance
import com.example.xfood.model.PopularCategoryList
import com.example.xfood.model.PopularCategoryMeals
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {

    private var mealByCategoryLiveData = MutableLiveData<List<PopularCategoryMeals>>()

    fun getMealsByCategory(categoryName: String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<PopularCategoryList>{
            override fun onResponse(
                call: Call<PopularCategoryList>,
                response: Response<PopularCategoryList>
            ) {
                if (response.body() != null){
                    mealByCategoryLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<PopularCategoryList>, t: Throwable) {
                Log.e("CategoryMealsViewModel", t.message.toString())
            }
        })
    }

    fun observeMealsByCategoryLiveData():LiveData<List<PopularCategoryMeals>>{
        return mealByCategoryLiveData
    }
}