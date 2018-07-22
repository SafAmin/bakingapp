package com.baking.thebaking.recipedeetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.thebaking.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Safa Amin on 7/22/2018.
 */

public class RecipeStepsFragment extends Fragment {

    @BindView(R.id.rv_recipe_steps)
    RecyclerView rvRecipeSteps;

    private Unbinder unbinder;

    public static RecipeStepsFragment getInstance() {
        RecipeStepsFragment fragment = new RecipeStepsFragment();

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_steps, parent, false);
        unbinder = ButterKnife.bind(this, view);
        if (savedInstanceState != null) {
        }
        Bundle args = getArguments();
        if (args != null) {
        }
        if (getActivity() != null) {
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        RecyclerView.LayoutManager stepsLayoutManager = new LinearLayoutManager(getContext());
        rvRecipeSteps.setLayoutManager(stepsLayoutManager);
        rvRecipeSteps.setNestedScrollingEnabled(false);
       /* rvRecipeSteps.setAdapter(new RecipeStepsAdapter(recipeStepsList,
                new RecipeStepsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick() {
                    }
                }));*/
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
