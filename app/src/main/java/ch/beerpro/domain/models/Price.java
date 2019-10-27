package ch.beerpro.domain.models;



import java.util.Date;

public class Price implements Entity {
    public static final String COLLECTION = "prices";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_CREATION_DATE = "creationDate";


    private String id;
    private String beerId;
    private String userId;
    private float price;
    private Date creationDate;

    public Price(){

    }

    public Price(String id, String beerId, String userId, float price, Date creationDate){

        this.id = id;
        this.beerId = beerId;
        this.userId = userId;
        this.price = price;
        this.creationDate = creationDate;

    }


    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}