package ch.beerpro.domain.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Beer implements Entity, Serializable {

    public static final String COLLECTION = "beers";
    public static final String FIELD_NAME = "name";

    private String id;
    private String manufacturer;
    private String name;
    private String category;
    private String photo;
    private float avgPrice;
    private float avgRating;
    private int numRatings;

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getCategory() {
        return category;
    }

    public String getPhoto() {
        return photo;
    }

    public float getAvgPrice() {
        return avgPrice;
    }

    public float getAvgRating() {
        return avgRating;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumRatings(){
        return this.numRatings;
    }
}
