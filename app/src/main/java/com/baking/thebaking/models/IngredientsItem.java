package com.baking.thebaking.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class IngredientsItem implements Parcelable {

    @SerializedName("ingredient")
    private String ingredient;

    @SerializedName("measure")
    private String measure;

    @SerializedName("quantity")
    private double quantity;

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getMeasure() {
        return measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ingredient);
        dest.writeString(this.measure);
        dest.writeDouble(this.quantity);
    }

    public IngredientsItem() {
    }

    protected IngredientsItem(Parcel in) {
        this.ingredient = in.readString();
        this.measure = in.readString();
        this.quantity = in.readDouble();
    }

    public static final Creator<IngredientsItem> CREATOR = new Creator<IngredientsItem>() {
        @Override
        public IngredientsItem createFromParcel(Parcel source) {
            return new IngredientsItem(source);
        }

        @Override
        public IngredientsItem[] newArray(int size) {
            return new IngredientsItem[size];
        }
    };
}