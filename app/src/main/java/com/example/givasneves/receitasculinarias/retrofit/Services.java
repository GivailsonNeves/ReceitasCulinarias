package com.example.givasneves.receitasculinarias.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Services {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<Object> listRecipes();
}
