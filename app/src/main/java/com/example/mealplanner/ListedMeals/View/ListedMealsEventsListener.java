package com.example.mealplanner.ListedMeals.View;

import com.example.mealplanner.Model.Meal.Meal;

public interface ListedMealsEventsListener {
    void onSavelClick(Meal meal);

    void onCardClick(Meal meal);
}
