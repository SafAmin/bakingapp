package com.baking.thebaking.recipesmainlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.baking.thebaking.IdlingResource.SimpleIdlingResource;
import com.baking.thebaking.R;
import com.baking.thebaking.base.BaseActivity;
import com.baking.thebaking.base.utils;
import com.baking.thebaking.models.Recipes;
import com.baking.thebaking.models.SelectRecipeModel;
import com.baking.thebaking.network.BakingRecipesAPI;
import com.baking.thebaking.network.BakingRecipesClient;
import com.baking.thebaking.recipedetails.RecipeDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RecipeMainListActivity extends BaseActivity {

    @BindView(R.id.rv_select_recipe)
    RecyclerView rvSelectRecipe;
    @BindString(R.string.select_recipes_title)
    String title;
    @BindString(R.string.recipes_error)
    String error;

    private List<SelectRecipeModel> recipeList;
    private BakingRecipesAPI service;
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        shouldDisplayHomeAsUpEnabled(false);
        setScreenTitle(title);
        service = BakingRecipesClient.getClientInstance().create(BakingRecipesAPI.class);
        initViews();
        showProgressDialog();
        getBakingRecipes();
    }

    public void initViews() {
        recipeList = new ArrayList<>();
        int numOfColumns = utils.calculateNoOfColumns(this);
        RecyclerView.LayoutManager recipesLayoutManager = new GridLayoutManager(this, numOfColumns);
        rvSelectRecipe.setLayoutManager(recipesLayoutManager);
        rvSelectRecipe.setNestedScrollingEnabled(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recipe_main_list;
    }

    public void getBakingRecipes() {
        Call<List<Recipes>> call = service.getBakingRecipes();
        call.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipes>> call,
                                   @NonNull Response<List<Recipes>> response) {
                generateRecipesDataList(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipes>> call, @NonNull Throwable t) {
                dismissProgressDialog();
                Timber.e("Exception ->" + t);
                showToast(error);
            }
        });
    }

    private void generateRecipesDataList(List<Recipes> recipes) {
        Recipes recipe;
        for (int i = 0; i < recipes.size(); i++) {
            recipe = recipes.get(i);
            recipeList.add(i, new SelectRecipeModel(recipe.getName(), recipe.getIngredients(),
                    recipe.getSteps()));
        }
        rvSelectRecipe.setAdapter(new RecipeListAdapter(recipeList,
                new RecipeListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(SelectRecipeModel item) {
                        navigateToDetails(item);
                    }
                }));
        dismissProgressDialog();
    }

    private void navigateToDetails(SelectRecipeModel item) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(SELECTED_RECIPE_PARAM, item);
        startActivity(intent);
    }
}
