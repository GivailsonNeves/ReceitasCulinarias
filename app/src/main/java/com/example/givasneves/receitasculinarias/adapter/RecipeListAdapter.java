package com.example.givasneves.receitasculinarias.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.givasneves.receitasculinarias.R;
import com.example.givasneves.receitasculinarias.model.Recipe;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;
    private OnRecipeClickListener mCallBack;

    public RecipeListAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public interface OnRecipeClickListener {
        void onItemClick(Recipe recipe);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recipe, viewGroup, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(v);

        try {
            mCallBack = (OnRecipeClickListener) viewGroup.getContext();
        } catch (ClassCastException e) {
            throw new ClassCastException(viewGroup.getContext().toString()
                    + "Must implement OnRecipeClickListener");
        }

        return recipeViewHolder;
    }

    @Override
    public int getItemCount() {
        return this.recipes.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {

        recipeViewHolder.position = i;
        recipeViewHolder.prepare(this.recipes.get(i));
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final View item;
        public int position;
        private TextView tvName;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvName = itemView.findViewById(R.id.tv_name);
            this.item = itemView.findViewById(R.id.item_recipe);
            this.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.onItemClick(recipes.get(position));
                }
            });
        }

        public void prepare(Recipe recipe) {
            tvName.setText(recipe.name);
        }
    }
}
