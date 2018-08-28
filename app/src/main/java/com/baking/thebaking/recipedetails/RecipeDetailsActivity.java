package com.baking.thebaking.recipedetails;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.baking.thebaking.R;
import com.baking.thebaking.base.BaseActivity;
import com.baking.thebaking.models.IngredientsItem;
import com.baking.thebaking.models.SelectRecipeModel;
import com.baking.thebaking.models.StepsItem;
import com.baking.thebaking.recipedetails.ingredientsandsteps.RecipeStepsFragment;
import com.baking.thebaking.recipedetails.recipestep.RecipeStepFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class RecipeDetailsActivity extends BaseActivity implements OnRecipeStepSelectedInterface {

    public static String SELECTED_RECIPE_INGREDIENTS_PARAM = "SELECTED_RECIPE_INGREDIENTS";
    public static String SELECTED_RECIPE_STEPS_PARAM = "SELECTED_RECIPE_STEPS";

    private List<IngredientsItem> ingredientsList;
    private List<StepsItem> stepsList;
    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        shouldDisplayHomeAsUpEnabled(true);
        if(savedInstanceState == null) {
            initViews();
        } else {
            ingredientsList = savedInstanceState.getParcelableArrayList(SELECTED_RECIPE_INGREDIENTS_PARAM);
            stepsList = savedInstanceState.getParcelableArrayList(SELECTED_RECIPE_STEPS_PARAM);
        }
    }

    public void initViews() {
        SelectRecipeModel selectRecipe = getIntent().getParcelableExtra(SELECTED_RECIPE_PARAM);
        setScreenTitle(selectRecipe.getRecipeName());
        ingredientsList = selectRecipe.getIngredientsList();
        stepsList = selectRecipe.getStepsList();
        displayRecipeDetailsFragment();
    }

    /**
     * This method handles Master Detail Flow.
     */
    private void displayRecipeDetailsFragment() {
        RecipeStepsFragment recipeStepsFragment = RecipeStepsFragment.
                getInstance(ingredientsList, stepsList);
        if (findViewById(R.id.layout_recipe_step_details) != null) {
            mTwoPane = true;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout_recipe_steps, recipeStepsFragment).commit();

        } else {
            mTwoPane = false;
            invalidateView(recipeStepsFragment);
        }
    }

    public void invalidateView(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_placeholder, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void OnRecipeStepSelected(StepsItem stepItem) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout_recipe_step_details, RecipeStepFragment.getInstance(stepItem,
                            null)).commit();
        } else {
            invalidateView(RecipeStepFragment.getInstance(stepItem, stepsList));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recipe_details;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putParcelableArrayList(SELECTED_RECIPE_INGREDIENTS_PARAM,
                (ArrayList<? extends Parcelable>) ingredientsList);
        currentState.putParcelableArrayList(SELECTED_RECIPE_STEPS_PARAM,
                (ArrayList<? extends Parcelable>) stepsList);
    }
}
