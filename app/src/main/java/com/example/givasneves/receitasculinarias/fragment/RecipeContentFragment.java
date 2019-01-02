package com.example.givasneves.receitasculinarias.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.givasneves.receitasculinarias.R;
import com.example.givasneves.receitasculinarias.adapter.RecipeListAdapter;
import com.example.givasneves.receitasculinarias.adapter.RecipeStepsAdapter;
import com.example.givasneves.receitasculinarias.model.Ingredient;
import com.example.givasneves.receitasculinarias.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeContentFragment extends Fragment {


    private Recipe recipe;

    @BindView(R.id.recipe_title) TextView tvTitle;
    @BindView(R.id.recipe_ingredients) TextView tvRecipeIngredients;
    @BindView(R.id.rvRecipeSteps) RecyclerView rvRecipeSteps;

    public RecipeContentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_content, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    private void prepare() {
        if(recipe != null) {
            this.tvTitle.setText(recipe.name);
            this.tvRecipeIngredients.setText(Html.fromHtml(recipe.getIngredientsHtml()));
            this.rvRecipeSteps.setAdapter(new RecipeStepsAdapter(recipe.steps));
            this.rvRecipeSteps.setLayoutManager(new LinearLayoutManager(this.getContext()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.prepare();
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}
