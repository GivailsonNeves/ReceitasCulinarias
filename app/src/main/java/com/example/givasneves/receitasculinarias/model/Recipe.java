package com.example.givasneves.receitasculinarias.model;

import java.util.List;

public class Recipe {

    public int id;
    public String name;
    public int servings;
    public String image;
    public List<Ingredient> ingredients;
    public List<Step> steps;
}
