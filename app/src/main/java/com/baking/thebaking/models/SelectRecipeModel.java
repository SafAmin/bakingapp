package com.baking.thebaking.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


/**
 * Created by Safa Amin on 7/22/2018.
 */

public class SelectRecipeModel implements Parcelable {

    private String recipeName;
    private List<IngredientsItem> ingredientsList;
    private List<StepsItem> stepsList;

    public SelectRecipeModel(String recipeName, List<IngredientsItem> ingredients, List<StepsItem> steps) {
        setRecipeName(recipeName);
        setIngredientsList(ingredients);
        setStepsList(steps);
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setIngredientsList(List<IngredientsItem> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<IngredientsItem> getIngredientsList() {
        return ingredientsList;
    }

    public void setStepsList(List<StepsItem> stepsList) {
        this.stepsList = stepsList;
    }

    public List<StepsItem> getStepsList() {
        return stepsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.recipeName);
        dest.writeTypedList(this.ingredientsList);
        dest.writeTypedList(this.stepsList);
    }

    protected SelectRecipeModel(Parcel in) {
        this.recipeName = in.readString();
        this.ingredientsList = in.createTypedArrayList(IngredientsItem.CREATOR);
        this.stepsList = in.createTypedArrayList(StepsItem.CREATOR);
    }

    public static final Creator<SelectRecipeModel> CREATOR = new Creator<SelectRecipeModel>() {
        @Override
        public SelectRecipeModel createFromParcel(Parcel source) {
            return new SelectRecipeModel(source);
        }

        @Override
        public SelectRecipeModel[] newArray(int size) {
            return new SelectRecipeModel[size];
        }
    };
}
