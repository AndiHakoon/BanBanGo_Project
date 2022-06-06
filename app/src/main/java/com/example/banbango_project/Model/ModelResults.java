package com.example.banbango_project.Model;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Annotation;

public class ModelResults implements SerializedName {

    @SerializedName("geometry")
    private ModelGeometry modelGeometry;

    @SerializedName("name")
    private String name;

    @SerializedName("vicinity")
    private String vicinity;

    @SerializedName("place_id")
    private String place_id;

    @SerializedName("rating")
    private String rating;

    public ModelGeometry getModelGeometry() {
        return modelGeometry;
    }

    public void setModelGeometry(ModelGeometry modelGeometry) {
        this.modelGeometry = modelGeometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String value() {
        return null;
    }

    @Override
    public String[] alternate() {
        return new String[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
