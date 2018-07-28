package com.baking.thebaking.selectrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.baking.thebaking.R;
import com.baking.thebaking.base.BaseActivity;
import com.baking.thebaking.models.Recipes;
import com.baking.thebaking.models.SelectRecipeModel;
import com.baking.thebaking.network.BakingRecipesAPI;
import com.baking.thebaking.network.BakingRecipesClient;
import com.baking.thebaking.recipedeetails.RecipeStepsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.baking.thebaking.utils.calculateNoOfColumns;

public class SelectRecipeActivity extends BaseActivity {

    @BindView(R.id.rv_select_recipe)
    RecyclerView rvSelectRecipe;
    @BindString(R.string.select_recipes_title)
    String title;
    @BindString(R.string.recipes_error)
    String error;

    private List<SelectRecipeModel> recipeList;
    private BakingRecipesAPI service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        service = BakingRecipesClient.getClientInstance().create(BakingRecipesAPI.class);
        showProgressDialog();
        getBakingRecipes();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_recipe;
    }

    @Override
    public void initViews() {
        setScreenTitle(title);
        recipeList = new ArrayList<>();
        int mNoOfColumns = calculateNoOfColumns(this);
        rvSelectRecipe = findViewById(R.id.rv_select_recipe);
        RecyclerView.LayoutManager recipesLayoutManager = new GridLayoutManager(this, mNoOfColumns);
        rvSelectRecipe.setLayoutManager(recipesLayoutManager);
        rvSelectRecipe.setNestedScrollingEnabled(false);
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
        for (int i = 0; i < recipes.size(); i++) {
            recipeList.add(i, new SelectRecipeModel(recipes.get(i).getName()));
        }
        rvSelectRecipe.setAdapter(new SelectRecipeAdapter(recipeList,
                new SelectRecipeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(SelectRecipeModel item) {
                        Intent intent = new Intent(SelectRecipeActivity.this, RecipeStepsActivity.class);
                        intent.putExtra(SELECTED_RECIPE_PARAM, item);
                        startActivity(intent);
                    }
                }));
        dismissProgressDialog();
    }
}
