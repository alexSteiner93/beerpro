package ch.beerpro.domain.models;


import java.util.Date;

public class MyBeerFromWishlist implements MyBeer {
    private Wish wish;
    private Beer beer;

    public MyBeerFromWishlist(Wish wish, Beer beer) {
        this.wish = wish;
        this.beer = beer;
    }


    @Override
    public String getBeerId() {
        return wish.getBeerId();
    }

    @Override
    public Date getDate() {
        return wish.getAddedAt();
    }

    @Override
    public Beer getBeer() {
        return beer;
    }
}