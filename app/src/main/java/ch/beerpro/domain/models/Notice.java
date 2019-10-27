package ch.beerpro.domain.models;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class Notice implements Entity {
    public static final String COLLECTION = "notices";
    public static final String FIELD_BEER_ID = "beerId";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_CREATION_DATE = "creationDate";

    private String id;
    private String beerId;
    private String beerName;
    private String userId;
    private String userName;
    private String userPhoto;
    private String comment;
    private Date creationDate;


    public Notice(String id, String beerId, String beerName, String userId, String userName,
                  String userPhoto, String comment, Date creationDate){

        this.id = id;
        this.beerId = beerId;
        this.beerName=beerName;
        this.userId = userId;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.comment = comment;
        this.creationDate = creationDate;

    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUserName(){
        return this.userName;
    }

    public String getComment(){
        return this.comment;
    }

    public Date getCreationDate(){
        return this.creationDate;
    }

    public String getUserPhoto(){
        return this.userPhoto;
    }

}