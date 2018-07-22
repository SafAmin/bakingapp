package com.baking.thebaking.models;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Safa Amin on 7/22/2018.
 */

public class SelectRecipeModel implements Parcelable {

    private String recipeName;

    public SelectRecipeModel(String recipeName) {
        setRecipeName(recipeName);
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.recipeName);
    }

    protected SelectRecipeModel(Parcel in) {
        this.recipeName = in.readString();
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
