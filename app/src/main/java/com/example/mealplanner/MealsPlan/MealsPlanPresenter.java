package com.example.mealplanner.MealsPlan;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.mealplanner.Database.Model.Favourite.FavouriteMeal;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.Database.Model.MealDate.MealDate;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Model.UserSession;

import java.util.ArrayList;
import java.util.List;

public class MealsPlanPresenter {

    private MealsPlanView view;
    private Repository repository;
    private List <LocalMeal> localMeals;

    public MealsPlanPresenter(MealsPlanView view, Repository repository) {
        this.view = view;
        this.repository = repository;
        this.localMeals = new ArrayList<>();
    }

    public void getMealsPlan(LifecycleOwner owner) {
        LiveData<List<MealDate>> favouriteMealsLiveData = repository.getDatedMealsByUserId(UserSession.getInstance().getUid());
        favouriteMealsLiveData.observe(owner, new Observer<List<MealDate>>() {
            @Override
            public void onChanged(List<MealDate> mealDates) {

                localMeals.clear();
                addMealPlansToLocal(mealDates);
            }
    });

    }

    private void addMealPlansToLocal(List<MealDate> mealDates) {
        for (MealDate mealDate : mealDates)
        {
            repository.getMealById(mealDate.mealId).observeForever(new Observer<LocalMeal>() {
                @Override
                public void onChanged(LocalMeal localMeal) {
                    localMeal.setDate(mealDate.date);
                    localMeals.add(localMeal);
                    view.displayMealsForDate(localMeals);
                }
            });
        }
    }


    public void deleteMeal(LocalMeal meal) {

        repository.deleteMealDate(meal);
    }
}
