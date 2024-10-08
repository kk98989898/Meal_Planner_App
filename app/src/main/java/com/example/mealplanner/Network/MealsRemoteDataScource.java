package com.example.mealplanner.Network;

import android.util.Log;

import com.example.mealplanner.Network.Model.Area.AreaResponse;
import com.example.mealplanner.Network.Model.Category.CategoryResponse;
import com.example.mealplanner.Network.Model.Ingredient.IngredientResponse;
import com.example.mealplanner.Network.NetworkListeners.MealDetailsNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.SearchCategory.IngredientsNetworkListener;
import com.example.mealplanner.Model.Meal.MealResponse;
import com.example.mealplanner.Network.NetworkListeners.SearchCategory.AreaNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.ListedMeals.MealsByAreaNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.ListedMeals.MealsByCategoryNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.ListedMeals.MealsByMainIngredientNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.SearchCategory.CategoryNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.RandomMealNetworkListener;
import com.example.mealplanner.RandomMeal.Presenter.RandomMealFragmentPresenter;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataScource {


    private static final String URL_STRING = "https://www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "MealsRemoteDataScource";
    private static MealsRemoteDataScource instance = null;
    private MealApiService mealApiService;

    private MealsRemoteDataScource() {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL_STRING).build();

        mealApiService = retrofit.create(MealApiService.class);
    }

    public static MealsRemoteDataScource getInstance() {

        if (instance == null) {
            instance = new MealsRemoteDataScource();
        }
        return instance;
    }

    public void getRandomMeal(RandomMealNetworkListener listener) {


        Call<MealResponse> call = mealApiService.getRandomMeal();
        call.enqueue(new Callback<MealResponse>() {


            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful())
                {
                   // Log.i(TAG, "onResponse: "+response.body());
                    MealResponse meal =  response.body();
                    //Log.i(TAG, "onResponse: "+response.raw().body());

                    //listener.onRandomMealSuccess(meal);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                //Log.i(TAG, "onFailure: ");
                listener.onRandomMealFailure(t.getMessage());
                //t.printStackTrace();
            }
        });

    }

    public void getCategories( CategoryNetworkListener listener){
        Call<CategoryResponse> call = mealApiService.getCategoriesList();
        call.enqueue(new Callback<CategoryResponse>(){
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                //Log.i(TAG, "onResponse: "+response.body());
                listener.onMealsCategorySuccess(response.body().getCategories());
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                listener.onMealsCategoryFailure(t.getMessage());
            }
        });

    }

    public void getAreas(AreaNetworkListener listener)
    {
        Call <AreaResponse> call = mealApiService.getAreasList();
        call.enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {


                if(response.isSuccessful())
                {
                    listener.onAreaSuccess(response.body().getAreas());
                    //Log.i(TAG, "onResponse / areas: "+response.body());
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {

                Log.i(TAG, "onFailure / areas: "+t.getMessage());
                //listener.onAreaFailure(t.getMessage());
            }
        });
    }

    public void getIngredients(IngredientsNetworkListener listener) {
        Call<IngredientResponse> call = mealApiService.getIngredientsList();
        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                //Log.i(TAG, "onResponse: "+response.body());
                listener.onIngredientsSuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                //Log.i(TAG, "onFailure: "+t.getMessage());
                listener.onIngredientsFailure(t.getMessage());

            }
        });
    }

    public void getMealsByMainIngredient(String ingredient, MealsByMainIngredientNetworkListener listener) {
        Call<MealResponse> call = mealApiService.getMealsByMainIngredient(ingredient);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                Log.i(TAG, "onResponse: "+response.body());
                listener.onMealsByMainIngredientSuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
                listener.onMealsByMainIngredientFailure(t.getMessage());

            }
        });

    }
    public void getMealsByCategory(String category, MealsByCategoryNetworkListener listener) {
        Call<MealResponse> call = mealApiService.getMealsByCategory(category);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                Log.i(TAG, "onResponse: "+ response.body());
                listener.onMealsByCategorySuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {

                Log.i(TAG, "onFailure: "+t.getMessage());
                listener.onMealsByCategoryFailure(t.getMessage());
            }
        });
    }

    public void getMealsByArea(String area, MealsByAreaNetworkListener listener) {
        Call<MealResponse> call = mealApiService.getMealsByArea(area);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                Log.i(TAG, "onResponse: "+response.body());
                listener.onMealsByAreaSuccess(response.body().getMeals());
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {

                Log.i(TAG, "onFailure: "+t.getMessage());
                listener.onMealsByAreaFailure(t.getMessage());
            }
        });
    }

    public void getMealDetails(String mealId, MealDetailsNetworkListener listener) {
        Call<MealResponse> call = mealApiService.getMealDetails(mealId);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                MealResponse mealResponse = response.body();
                Log.i(TAG, "onResponse: "+new Gson().toJson(mealResponse));
                Log.i(TAG, "onResponse ID: "+response.body());
                listener.onMealDetailsSuccess(response.body().getMeals().get(0));
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
                listener.onMealDetailsFailure(t.getMessage());
            }
        });

    }

    public void getMultipleRandomMeals(RandomMealNetworkListener listener) {

        for (int i = 0; i < 10; i++) {
            Call<MealResponse> call = mealApiService.getRandomMeal();
            call.enqueue(new Callback<MealResponse>() {


                @Override
                public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                    if (response.isSuccessful()) {
                        // Log.i(TAG, "onResponse: "+response.body());
                        MealResponse meal = response.body();
                        //Log.i(TAG, "onResponse: "+response.raw().body());

                        listener.onMealListSuccess(meal.getMeals().get(0));
                    }
                }

                @Override
                public void onFailure(Call<MealResponse> call, Throwable t) {
                    //Log.i(TAG, "onFailure: ");
                    listener.onRandomMealFailure(t.getMessage());
                    //t.printStackTrace();
                }
            });


        }
    }


}
