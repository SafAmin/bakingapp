package com.baking.thebaking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipes implements Parcelable {

    @SerializedName("image")
    private String image;

    @SerializedName("servings")
    private double servings;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private List<IngredientsItem> ingredients;

    @SerializedName("id")
    private int id;

    @SerializedName("steps")
    private List<StepsItem> steps;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setServings(double servings) {
        this.servings = servings;
    }

    public double getServings() {
        return servings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIngredients(List<IngredientsItem> ingredients) {
        this.ingredients = ingredients;
    }

    public List<IngredientsItem> getIngredients() {
        return ingredients;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSteps(List<StepsItem> steps) {
        this.steps = steps;
    }

    public List<StepsItem> getSteps() {
        return steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeDouble(this.servings);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeInt(this.id);
        dest.writeTypedList(this.steps);
    }

    public Recipes() {
    }

    protected Recipes(Parcel in) {
        this.image = in.readString();
        this.servings = in.readInt();
        this.name = in.readString();
        this.ingredients = new ArrayList<IngredientsItem>();
        in.readList(this.ingredients, IngredientsItem.class.getClassLoader());
        this.id = in.readInt();
        this.steps = in.createTypedArrayList(StepsItem.CREATOR);
    }

    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel source) {
            return new Recipes(source);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };
}