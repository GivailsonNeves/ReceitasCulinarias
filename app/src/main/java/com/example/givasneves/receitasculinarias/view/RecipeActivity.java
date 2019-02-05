package com.example.givasneves.receitasculinarias.view;

import android.content.Intent;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.givasneves.receitasculinarias.R;
import com.example.givasneves.receitasculinarias.adapter.RecipeStepsAdapter;
import com.example.givasneves.receitasculinarias.fragment.RecipeContentFragment;
import com.example.givasneves.receitasculinarias.model.Recipe;
import com.example.givasneves.receitasculinarias.model.Step;

public class RecipeActivity extends AppCompatActivity implements RecipeStepsAdapter.OnStepClickListener {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        this.requestRecipe(savedInstanceState);
        this.prepareView();
    }

    private void requestRecipe(Bundle savedInstanceState) {

        Intent intent = getIntent();
        this.recipe = intent.getParcelableExtra("recipe");
        if(this.recipe == null && savedInstanceState != null) {
            this.recipe = savedInstanceState.getParcelable("recipe");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("recipe", this.recipe);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null)
            this.recipe = savedInstanceState.getParcelable("recipe");
    }

    private void prepareView() {

        RecipeContentFragment recipeContentFragment = new RecipeContentFragment();
        recipeContentFragment.setRecipe(this.recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_content, recipeContentFragment)
                .commit();


    }

    @Override
    public void onItemClick(Step step) {
        Intent intentVideoPlayer = new Intent(this, VideoPlayerActivity.class);

        intentVideoPlayer.putExtra(getString(R.string.parceable_step), step);
        intentVideoPlayer.putExtra("recipe", recipe);
        startActivity(intentVideoPlayer);
    }
}
