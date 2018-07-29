package com.baking.thebaking.recipedeetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.baking.thebaking.R;
import com.baking.thebaking.base.BaseActivity;
import com.baking.thebaking.models.SelectRecipeModel;
import com.baking.thebaking.recipedeetails.ingredientsandsteps.RecipeStepsFragment;

import butterknife.ButterKnife;

public class RecipeStepsActivity extends BaseActivity {

    private SelectRecipeModel selectRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        shouldDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        selectRecipe = intent.getParcelableExtra(SELECTED_RECIPE_PARAM);
        setScreenTitle(selectRecipe.getRecipeName());
        initViews();
    }

    public void initViews() {
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
