
package ch.beerpro.domain.models;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class Fridge implements Entity {
    public static final String COLLECTION = "fridge";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_CREATION_DATE = "creationDate";
    public static final String FIELD_amount = "amount";

    @Exclude
    private String id;
    private String userId;
    private String amount;
    private String beerId;

    private Date creationDate = new Date();

    public Fridge(){

    }

    public Fridge(String id, String userId, String amount, String beerId){
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.beerId = beerId;
    }

    public Fridge(String userId, String amount, String beerId){
        this.userId = userId;
        this.amount = amount;
        this.beerId = beerId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void setBeerId(String beerId) {
        this.beerId = beerId;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public String getBeerId() {
        return beerId;
    }

    public static String generateId(String userId, String beerId) {
        return String.format("%s_%s", userId, beerId);
    }
}