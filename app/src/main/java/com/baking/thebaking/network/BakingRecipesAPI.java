package com.baking.thebaking.network;

import com.baking.thebaking.models.Recipes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Safa Amin on 7/22/2018.
 */

public interface BakingRecipesAPI {

    @GET("baking.json")
    Call<List<Recipes>> getBakingRecipes();
}
