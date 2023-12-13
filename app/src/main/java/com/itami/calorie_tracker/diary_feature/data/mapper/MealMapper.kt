package com.itami.calorie_tracker.diary_feature.data.mapper

import com.itami.calorie_tracker.diary_feature.data.local.entity.ConsumedFoodEntity
import com.itami.calorie_tracker.diary_feature.data.local.entity.ConsumedFoodWithFood
import com.itami.calorie_tracker.diary_feature.data.local.entity.FoodEntity
import com.itami.calorie_tracker.diary_feature.data.local.entity.MealEntity
import com.itami.calorie_tracker.diary_feature.data.local.entity.MealWithConsumedFoods
import com.itami.calorie_tracker.diary_feature.data.remote.request.ConsumedFoodRequest
import com.itami.calorie_tracker.diary_feature.data.remote.request.CreateMealRequest
import com.itami.calorie_tracker.diary_feature.data.remote.request.UpdateMealRequest
import com.itami.calorie_tracker.diary_feature.data.remote.response.ConsumedFoodResponse
import com.itami.calorie_tracker.diary_feature.data.remote.response.FoodResponse
import com.itami.calorie_tracker.diary_feature.data.remote.response.MealResponse
import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood
import com.itami.calorie_tracker.diary_feature.domain.model.CreateConsumedFood
import com.itami.calorie_tracker.diary_feature.domain.model.CreateMeal
import com.itami.calorie_tracker.diary_feature.domain.model.Food
import com.itami.calorie_tracker.diary_feature.domain.model.Meal
import com.itami.calorie_tracker.diary_feature.domain.model.UpdateMeal
import java.time.ZonedDateTime


fun MealResponse.toMealEntity() = MealEntity(
    id = this.id,
    name = this.name,
    utcCreatedAt = ZonedDateTime.parse(this.createdAt)
)

fun FoodResponse.toFoodEntity() = FoodEntity(
    id = this.id,
    name = this.name,
    caloriesIn100Grams = this.caloriesIn100Grams,
    proteinsIn100Grams = this.proteinsIn100Grams,
    fatsIn100Grams = this.fatsIn100Grams,
    carbsIn100Grams = this.carbsIn100Grams
)

fun FoodResponse.toFood() = Food(
    id = this.id,
    name = this.name,
    caloriesIn100Grams = this.caloriesIn100Grams,
    proteinsIn100Grams = this.proteinsIn100Grams,
    fatsIn100Grams = this.fatsIn100Grams,
    carbsIn100Grams = this.carbsIn100Grams
)

fun ConsumedFoodResponse.toConsumedFoodEntity(mealId: Int) = ConsumedFoodEntity(
    id = this.id,
    mealId = mealId,
    foodId = this.food.id,
    grams = this.grams
)

fun MealWithConsumedFoods.toMeal() = Meal(
    id = this.mealEntity.id,
    name = this.mealEntity.name,
    consumedFoods = this.consumedFoods.map { it.toConsumedFood() },
    createdAt = this.mealEntity.utcCreatedAt.toString()
)

fun ConsumedFoodWithFood.toConsumedFood() = ConsumedFood(
    id = this.consumedFood.foodId,
    food = this.food.toFood(),
    grams = this.consumedFood.grams
)

fun FoodEntity.toFood() = Food(
    id = this.id,
    name = this.name,
    caloriesIn100Grams = this.caloriesIn100Grams,
    proteinsIn100Grams = this.proteinsIn100Grams,
    fatsIn100Grams = this.fatsIn100Grams,
    carbsIn100Grams = this.carbsIn100Grams
)

fun CreateMeal.toCreateMealRequest() = CreateMealRequest(
    name = this.name,
    consumedFoods = this.consumedFoods.map { it.toConsumedFoodRequest() },
    datetime = this.createAt
)

fun UpdateMeal.toUpdateMealRequest() = UpdateMealRequest(
    name = this.name,
    consumedFoods = this.consumedFoods.map { it.toConsumedFoodRequest() },
)

fun CreateConsumedFood.toConsumedFoodRequest() = ConsumedFoodRequest(
    foodId = this.foodId,
    grams = this.grams
)