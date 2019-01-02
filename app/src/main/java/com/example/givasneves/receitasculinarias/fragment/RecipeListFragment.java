package com.example.givasneves.receitasculinarias.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.givasneves.receitasculinarias.R;
import com.example.givasneves.receitasculinarias.adapter.RecipeListAdapter;
import com.example.givasneves.receitasculinarias.model.Recipe;
import com.example.givasneves.receitasculinarias.retrofit.RetrofitInitializer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecipeListFragment extends Fragment implements Callback<List<Recipe>> {


    private final String TAG = RecipeListFragment.class.getName();

    RecyclerView rvRecipeList;

    public RecipeListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        this.rvRecipeList = v.findViewById(R.id.rv_recipe_list);
        this.rvRecipeList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadRecipes();
    }

    private void loadRecipes() {
        new RetrofitInitializer().services
                .listRecipes()
                .enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        Log.i(TAG, Integer.toString(response.code()));
        if(response.code() == 200)
            this.prepareList(response.body());
        else
            Toast.makeText(this.getActivity(), R.string.error_load_recipes, Toast.LENGTH_LONG);
    }

    private void prepareList(List<Recipe> body) {
        this.rvRecipeList.setAdapter(new RecipeListAdapter(body));
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        Log.i(TAG, t.getMessage());
    }
}
