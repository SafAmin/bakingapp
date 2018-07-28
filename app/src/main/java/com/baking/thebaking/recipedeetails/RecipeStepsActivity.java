package com.baking.thebaking.recipedeetails;

import android.content.Intent;
import android.os.Bundle;

import com.baking.thebaking.R;
import com.baking.thebaking.base.BaseActivity;
import com.baking.thebaking.models.SelectRecipeModel;

import butterknife.ButterKnife;

public class RecipeStepsActivity extends BaseActivity {

    private SelectRecipeModel selectRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        selectRecipe = intent.getParcelableExtra(SELECTED_RECIPE_PARAM);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recipe_steps;
    }

    @Override
    public void initViews() {

    }
}
