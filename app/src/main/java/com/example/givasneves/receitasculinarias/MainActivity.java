package com.example.givasneves.receitasculinarias;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.givasneves.receitasculinarias.adapter.RecipeListAdapter;
import com.example.givasneves.receitasculinarias.fragment.RecipeContentFragment;
import com.example.givasneves.receitasculinarias.fragment.RecipeListFragment;
import com.example.givasneves.receitasculinarias.model.Recipe;
import com.example.givasneves.receitasculinarias.view.RecipeActivity;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.OnRecipeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onItemClick(Recipe recipe) {
        Toast.makeText(this, recipe.name, Toast.LENGTH_SHORT).show();
        View view = findViewById(R.id.recipe_content_fragment);

        if( view == null ) {

            Intent intent = new Intent(this, RecipeActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);

        } else {

            RecipeContentFragment recipeContentFragment = new RecipeContentFragment();
            recipeContentFragment.setRecipe(recipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_content_fragment, recipeContentFragment)
                    .commit();


        }
    }
}
