package ch.beerpro.domain.models;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;


import java.util.Date;

@IgnoreExtraProperties

public class Wish implements Entity {

    public static final String COLLECTION = "wishes";
    public static final String FIELD_ID = "id";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_BEER_ID = "beerId";
    public static final String FIELD_ADDED_AT = "addedAt";

    /**
     * The id is formed by `$userId_$beerId` to make queries easier.
     */
    @Exclude
    private String id;
    private String userId;
    private String beerId;
    private Date addedAt;

    public static String generateId(String userId, String beerId) {
        return String.format("%s_%s", userId, beerId);
    }

    public Wish(String userId, String beerId, Date date){

        this.userId = userId;
        this.beerId = beerId;
        this.addedAt = date;

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public String getBeerId() {
        return beerId;
    }
}