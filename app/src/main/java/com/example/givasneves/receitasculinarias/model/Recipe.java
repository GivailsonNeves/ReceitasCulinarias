package com.example.givasneves.receitasculinarias.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {

    public int id;
    public String name;
    public int servings;
    public String image;
    public List<Ingredient> ingredients;
    public List<Step> steps;


    public Recipe() {}

    public Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
        ingredients = in.readArrayList(Ingredient.class.getClassLoader());
        steps = in.readArrayList(Step.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(image);
        dest.writeList(ingredients);
        dest.writeList(steps);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
