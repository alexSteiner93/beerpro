package ch.beerpro.domain.models;

import com.google.firebase.firestore.Exclude;
import java.util.Date;

public class FridgeEntry implements Entity {

    public static final String COLLECTION = "fridge";
    public static final String FIELD_ID = "id";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_BEER_ID = "beerId";
    public static final String FIELD_ADDED_AT = "addedAt";
    public static final String AMOUNT_BEER = "amount";


    @Exclude
    private String id;
    private String userId;
    private String beerId;
    private int amount;
    private Date addedAt;

    public FridgeEntry(String userId, String itemId, int i, Date date) {
        this.userId= userId;
        this.beerId = itemId;
        this.amount = i;
        this.addedAt = date;
    }


    @Override
    public void setId(String id) {
        this.id = id;
    }

    public static String generateId(String userId, String beerId) {
        return String.format("%s_%s", userId, beerId);
    }

    public String getBeerId() {
        return beerId;
    }


    public Date getAddedAt() {
        return addedAt;
    }

    @Override
    public String getId() {
        return id;
    }
}