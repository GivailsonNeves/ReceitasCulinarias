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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

        public int position;

        @BindView(R.id.tv_name) public TextView tvName;
        @BindView(R.id.tv_serving) public TextView tvServing;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void prepare(Recipe recipe) {

            tvName.setText(recipe.name);
            tvServing.setText(Integer.toString(recipe.servings));
        }

        @OnClick(R.id.item_recipe)
        public void handleClick() {
            mCallBack.onItemClick(recipes.get(position));
        }
    }
}
