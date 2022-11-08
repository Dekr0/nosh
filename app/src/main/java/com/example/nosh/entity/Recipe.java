package com.example.nosh.entity;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;


public class Recipe extends Foodstuff implements Hashable {

    double preparationTime;
    int servings;
    String category = "";
    String comments = "";
    String photograph = ""; // Image reference in storage

    ArrayList<Ingredient> ingredients = new ArrayList<>();

    String hashcode;

    public Recipe() {
        super("NULL");
    }

    public Recipe(double preparationTime, int servings, String category,
                  String comments, String photograph, String name,
                  ArrayList<Ingredient> ingredients) {
        super(name);
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.category = category;
        this.comments = comments;
        this.photograph = photograph;

        this.ingredients = ingredients;

        hashcode = Hashing.sha256().hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
    }

    public double getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(double preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPhotograph() {
        return photograph;
    }

    public void setPhotograph(String photograph) {
        this.photograph = photograph;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }
}
