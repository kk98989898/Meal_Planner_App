package com.example.mealplanner.MealsPlan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

import java.util.List;

public class MealsPlanAdapter extends RecyclerView.Adapter<MealsPlanAdapter.MealsPlanViewHolder> {

    private List<LocalMeal> savedMealList;
    private Context context;
    private MealsByDateEventListener listener;

    // Updated constructor
    public MealsPlanAdapter(List<LocalMeal> savedMealList, Context context, MealsByDateEventListener listener) {
        this.savedMealList = savedMealList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealsPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_plan_row, parent, false);
        return new MealsPlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsPlanViewHolder holder, int position) {
        LocalMeal meal = savedMealList.get(position);
        holder.mealName.setText(meal.getName());
        holder.mealCard.setOnClickListener(v -> listener.onCardClick(meal));
        holder.removeButton.setOnClickListener(v -> listener.onRemoveButtonClick(meal));
        holder.mealDate.setText(meal.getDate());

        Glide.with(context)
                .load(meal.getThumbnail())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mealThumbnail);

        holder.areaChip.setText(meal.getArea());
        holder.categoryChip.setText(meal.getCategory());


    }

    @Override
    public int getItemCount() {
        return savedMealList.size();
    }

    public void setMealsPlanList(List<LocalMeal> meals) {
        savedMealList = meals;
        notifyDataSetChanged();
    }

    public class MealsPlanViewHolder extends RecyclerView.ViewHolder {
        ImageView mealThumbnail;
        TextView mealName;
        CardView mealCard;
        MaterialButton removeButton;
        Chip areaChip;
        Chip categoryChip;
        TextView mealDate;

        public MealsPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            mealThumbnail = itemView.findViewById(R.id.mealThumbnail);
            mealName = itemView.findViewById(R.id.mealName);
            mealCard = itemView.findViewById(R.id.mealCard);
            removeButton = itemView.findViewById(R.id.removeButton);
            areaChip = itemView.findViewById(R.id.mealArea);
            categoryChip = itemView.findViewById(R.id.mealCategory);
            mealDate = itemView.findViewById(R.id.mealDate);
        }
    }
}
