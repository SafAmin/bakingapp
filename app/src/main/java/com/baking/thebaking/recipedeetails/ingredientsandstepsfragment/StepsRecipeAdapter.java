package com.baking.thebaking.recipedeetails.ingredientsandstepsfragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baking.thebaking.R;
import com.baking.thebaking.models.StepsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Adapter responsible for making a View for each item in the selected recipe details RecyclerView
 * within {@link RecipeStepsFragment}
 * <p>
 * Created by Safa Amin on 7/22/2018.
 */

public class StepsRecipeAdapter extends RecyclerView.Adapter<StepsRecipeAdapter.ViewHolder> {

    private List<StepsItem> stepsList;
    private final OnItemClickListener listener;

    public StepsRecipeAdapter(List<StepsItem> steps, OnItemClickListener listener) {
        this.stepsList = steps;
        this.listener = listener;
    }

    @Override
    @NonNull
    public StepsRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.recipe_steps_item_view, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StepsItem model = stepsList.get(holder.getAdapterPosition());
        holder.bindData(model, listener);
    }

    public void add(int position, StepsItem item) {
        stepsList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        stepsList.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_recipe_steps_description)
        TextView tvRecipeStepsDescription;

        ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bindData(final StepsItem model, final OnItemClickListener listener) {
            tvRecipeStepsDescription.setText(model.getShortDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(model);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(StepsItem item);
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
