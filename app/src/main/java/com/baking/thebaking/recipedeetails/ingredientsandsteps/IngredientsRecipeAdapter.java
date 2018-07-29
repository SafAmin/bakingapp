package com.baking.thebaking.recipedeetails.ingredientsandsteps;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baking.thebaking.R;
import com.baking.thebaking.models.IngredientsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Adapter responsible for making a View for each item in the selected recipe details RecyclerView
 * within {@link RecipeStepsFragment}
 * <p>
 * Created by Safa Amin on 7/22/2018.
 */

public class IngredientsRecipeAdapter extends RecyclerView.Adapter<IngredientsRecipeAdapter.ViewHolder> {

    private List<IngredientsItem> ingredientsList;
    private final OnItemClickListener listener;

    IngredientsRecipeAdapter(List<IngredientsItem> ingredients, OnItemClickListener listener) {
        this.ingredientsList = ingredients;
        this.listener = listener;
    }

    @Override
    @NonNull
    public IngredientsRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.recipe_ingredients_item_view, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientsItem model = ingredientsList.get(holder.getAdapterPosition());
        holder.bindData(model, listener);
    }

    public void add(int position, IngredientsItem item) {
        ingredientsList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        ingredientsList.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_recipe_ingredients_ingredient)
        TextView tvRecipeIngredient;
        @BindView(R.id.tv_recipe_ingredients_quantity)
        TextView tvRecipeIngredientSQuantity;
        @BindView(R.id.view_recipe_ingredients_separator)
        View viewIngredientsSeparator;

        ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bindData(final IngredientsItem model, final OnItemClickListener listener) {
            tvRecipeIngredient.setText(model.getIngredient());
            tvRecipeIngredientSQuantity.setText(model.getQuantity() + " " + model.getMeasure());
            if (getAdapterPosition() == ingredientsList.size()) {
                viewIngredientsSeparator.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(model);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(IngredientsItem item);
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
