package com.example.givasneves.receitasculinarias.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.givasneves.receitasculinarias.R;
import com.example.givasneves.receitasculinarias.model.Recipe;


public class RecipeContentFragment extends Fragment {


    private Recipe recipe;

    TextView tvTitle;

    public RecipeContentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_content, container, false);
        tvTitle = v.findViewById(R.id.recipe_title);
        return v;
    }

    private void prepare() {
        if(recipe != null)
            this.tvTitle.setText(recipe.name);
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
