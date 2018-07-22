package com.baking.thebaking.selectrecipe;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baking.thebaking.R;
import com.baking.thebaking.models.SelectRecipeModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Adapter responsible for making a View for each item in the select recipe RecyclerView
 * within {@link SelectRecipeActivity}
 * <p>
 * Created by Safa Amin on 7/22/2018.
 */

public class SelectRecipeAdapter extends RecyclerView.Adapter<SelectRecipeAdapter.ViewHolder> {

    private List<SelectRecipeModel> recipeList;
    private final OnItemClickListener listener;

    SelectRecipeAdapter(List<SelectRecipeModel> recipeList, OnItemClickListener listener) {
        this.recipeList = recipeList;
        this.listener = listener;
    }

    @Override
    @NonNull
    public SelectRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.select_recipe_item_view, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelectRecipeModel model = recipeList.get(holder.getAdapterPosition());
        holder.bindData(model, listener);
    }

    public void add(int position, SelectRecipeModel item) {
        recipeList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        recipeList.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_recipe_name)
        TextView tvMovieName;

        ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bindData(final SelectRecipeModel model, final OnItemClickListener listener) {
           /* Picasso.get().load(moviePosterBaseURL +
                    model.getMoviePoster()).into(ivMoviePoster);*/
            tvMovieName.setText(model.getRecipeName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(model);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SelectRecipeModel item);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
