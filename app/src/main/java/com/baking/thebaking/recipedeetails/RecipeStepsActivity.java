package com.baking.thebaking.recipedeetails;

import android.content.Intent;
import android.os.Bundle;

import com.baking.thebaking.R;
import com.baking.thebaking.base.ToolbarActivity;
import com.baking.thebaking.models.SelectRecipeModel;

import butterknife.ButterKnife;

public class RecipeStepsActivity extends ToolbarActivity {

    private SelectRecipeModel selectRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected int getToolbarLayoutResource() {
        return R.layout.activity_recipe_steps;
    }
}
