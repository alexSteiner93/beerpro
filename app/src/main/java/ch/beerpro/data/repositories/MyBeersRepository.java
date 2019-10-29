package ch.beerpro.data.repositories;

import androidx.lifecycle.LiveData;

import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Entity;
import ch.beerpro.domain.models.FridgeBeer;
import ch.beerpro.domain.models.MyBeer;
import ch.beerpro.domain.models.MyBeerFromRating;
import ch.beerpro.domain.models.MyBeerFromWishlist;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.domain.models.MyBeerFromFridge;

import static androidx.lifecycle.Transformations.map;
import static ch.beerpro.domain.utils.LiveDataExtensions.combineLatest;

public class MyBeersRepository {


    Triple<Object, Object, Object> test;

    private static List<MyBeer> getMyBeers(Quadruple<List<Wish>, List<Rating>, List<FridgeBeer>, HashMap<String, Beer>> input) {
        List<Wish> wishlist = input.getFirst();
        List<Rating> ratings = input.getSecond();
        List<FridgeBeer> fridgeBeers = input.getThird();
        HashMap<String, Beer> beers = input.getFourth();

        ArrayList<MyBeer> result = new ArrayList<>();
        Set<String> beersAlreadyOnTheList = new HashSet<>();
        for (Wish wish : wishlist) {
            String beerId = wish.getBeerId();
            result.add(new MyBeerFromWishlist(wish, beers.get(beerId)));
            beersAlreadyOnTheList.add(beerId);
        }

        for (Rating rating : ratings) {
            String beerId = rating.getBeerId();
            if (!beersAlreadyOnTheList.contains(beerId)) {
                result.add(new MyBeerFromRating(rating, beers.get(beerId)));
                beersAlreadyOnTheList.add(beerId);
            }
        }

        for (FridgeBeer f : fridgeBeers) {
            String beerId = f.getBeerId();
            if (!beersAlreadyOnTheList.contains(beerId)) {
                result.add(new MyBeerFromFridge(f, beers.get(beerId)));
                beersAlreadyOnTheList.add(beerId);
            }
        }

        Collections.sort(result, (r1, r2) -> r2.getDate().compareTo(r1.getDate()));
        return result;
    }

    public LiveData<List<MyBeer>> getMyBeers(LiveData<List<Beer>> allBeers, LiveData<List<Wish>> myWishlist, LiveData<List<Rating>> myRatings, LiveData<List<FridgeBeer>> myFridgeBeers) {
        return map(combineLatest(myWishlist, myRatings, myFridgeBeers, map(allBeers, Entity::entitiesById)), MyBeersRepository::getMyBeers);
    }
}