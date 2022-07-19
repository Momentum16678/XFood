package com.example.xfood.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.xfood.model.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM MealInformation")
    fun getAllMeals():LiveData<List<Meal>>

}