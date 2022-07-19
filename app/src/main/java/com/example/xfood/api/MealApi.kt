package com.example.xfood.api

import com.example.xfood.model.CategoryList
import com.example.xfood.model.PopularCategoryList
import com.example.xfood.model.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {


    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i")id: String):Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c")categoryName:String):Call<PopularCategoryList>

    @GET("categories.php")
    fun getCategoryMeal():Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c")categoryName:String):Call<PopularCategoryList>
}