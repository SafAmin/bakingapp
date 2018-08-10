package com.baking.thebaking.recipedetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.baking.thebaking.R;
import com.baking.thebaking.base.BaseActivity;
import com.baking.thebaking.models.SelectRecipeModel;
import com.baking.thebaking.recipedetails.ingredientsandstepsfragment.RecipeStepsFragment;

import butterknife.ButterKnife;

public class RecipeStepsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        shouldDisplayHomeAsUpEnabled(true);
        initViews();
    }

    public void initViews() {
        SelectRecipeModel selectRecipe = getIntent().getParcelableExtra(SELECTED_RECIPE_PARAM);
        setScreenTitle(selectRecipe.getRecipeName());
        invalidateView(RecipeStepsFragment.getInstance(selectRecipe.getIngredientsList(),
                selectRecipe.getStepsList()));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recipe_steps;
    }

    public void invalidateView(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_placeholder, fragment);
        fragmentTransaction.commit();
    }
}
