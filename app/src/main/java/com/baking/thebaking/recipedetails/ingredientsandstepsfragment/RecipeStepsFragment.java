package com.baking.thebaking.recipedetails.ingredientsandstepsfragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.thebaking.R;
import com.baking.thebaking.models.IngredientsItem;
import com.baking.thebaking.models.StepsItem;
import com.baking.thebaking.recipedetails.RecipeDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Safa Amin on 7/22/2018.
 */

public class RecipeStepsFragment extends Fragment {

    public static String SELECTED_RECIPE_INGREDIENTS_PARAM = "SELECTED_RECIPE_INGREDIENTS";
    public static String SELECTED_RECIPE_STEPS_PARAM = "SELECTED_RECIPE_STEPS";

    @BindView(R.id.rv_recipe_ingredients)
    RecyclerView rvRecipeIngredients;
    @BindView(R.id.rv_recipe_steps)
    RecyclerView rvRecipeSteps;

    private Unbinder unbinder;
    private List<IngredientsItem> ingredientsList;
    private List<StepsItem> stepsList;

    public static RecipeStepsFragment getInstance(List<IngredientsItem> ingredients, List<StepsItem> steps) {
        RecipeStepsFragment fragment = new RecipeStepsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(SELECTED_RECIPE_INGREDIENTS_PARAM, (ArrayList<? extends Parcelable>) ingredients);
        args.putParcelableArrayList(SELECTED_RECIPE_STEPS_PARAM, (ArrayList<? extends Parcelable>) steps);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_steps, parent, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle args = getArguments();
        if (args != null) {
            ingredientsList = args.getParcelableArrayList(SELECTED_RECIPE_INGREDIENTS_PARAM);
            stepsList = args.getParcelableArrayList(SELECTED_RECIPE_STEPS_PARAM);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initViews();
    }

    private void initViews() {
        RecyclerView.LayoutManager ingredientsLayoutManager = new LinearLayoutManager(getContext());
        rvRecipeIngredients.setLayoutManager(ingredientsLayoutManager);
        rvRecipeIngredients.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager stepsLayoutManager = new LinearLayoutManager(getContext());
        rvRecipeSteps.setLayoutManager(stepsLayoutManager);
        rvRecipeSteps.setNestedScrollingEnabled(false);

        invalidateViews();
    }

    private void invalidateViews() {
        rvRecipeIngredients.setAdapter(new IngredientsRecipeAdapter(ingredientsList,
                new IngredientsRecipeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(IngredientsItem item) {
                    }
                }));

        rvRecipeSteps.setAdapter(new StepsRecipeAdapter(stepsList,
                new StepsRecipeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(StepsItem item) {
                        if (((RecipeDetailsActivity) getActivity()) != null) {
                            ((RecipeDetailsActivity) getActivity()).OnRecipeStepSelected(item);
                        }
                    }
                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
