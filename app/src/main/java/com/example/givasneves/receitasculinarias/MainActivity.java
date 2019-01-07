package com.example.givasneves.receitasculinarias;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.givasneves.receitasculinarias.adapter.RecipeListAdapter;
import com.example.givasneves.receitasculinarias.adapter.RecipeStepsAdapter;
import com.example.givasneves.receitasculinarias.fragment.RecipeContentFragment;
import com.example.givasneves.receitasculinarias.model.Recipe;
import com.example.givasneves.receitasculinarias.model.Step;
import com.example.givasneves.receitasculinarias.view.RecipeActivity;
import com.example.givasneves.receitasculinarias.view.VideoPlayerActivity;
import com.example.givasneves.receitasculinarias.widget.RecipeWidgetProvider;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.OnRecipeClickListener, RecipeStepsAdapter.OnStepClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onItemClick(Recipe recipe) {

        View view = findViewById(R.id.recipe_content_fragment);

        sendRecipeSelectedToWidget(recipe);

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

    private void sendRecipeSelectedToWidget(Recipe recipe) {
        Intent intentRecipeSelected = new Intent(RecipeWidgetProvider.RECIPE_SELECTED);
        intentRecipeSelected.putExtra(RecipeWidgetProvider.RECIPE_PARCEABLE_NAME, recipe);
        getApplicationContext().sendBroadcast(intentRecipeSelected);
    }

    @Override
    public void onItemClick(Step step) {
        Intent intentVideoPlayer = new Intent(this, VideoPlayerActivity.class);
        intentVideoPlayer.putExtra(getString(R.string.parceable_step), step);
        startActivity(intentVideoPlayer);
    }
}
