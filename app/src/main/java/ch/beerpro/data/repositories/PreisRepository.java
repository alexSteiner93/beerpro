package ch.beerpro.data.repositories;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

import ch.beerpro.domain.models.Preis;
import ch.beerpro.domain.utils.FirestoreQueryLiveDataArray;

import static androidx.lifecycle.Transformations.switchMap;

public class PreisRepository {

    private final FirestoreQueryLiveDataArray<Preis> allPrices = new FirestoreQueryLiveDataArray<>(
            FirebaseFirestore.getInstance().collection(Preis.COLLECTION)
                    .orderBy(Preis.FIELD_CREATION_DATE, Query.Direction.DESCENDING), Preis.class);

    public static LiveData<List<Preis>> getPricesByUser(String userId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Preis.COLLECTION)
                .orderBy(Preis.FIELD_CREATION_DATE, Query.Direction.DESCENDING)
                .whereEqualTo(Preis.FIELD_USER_ID, userId), Preis.class);
    }


    public static LiveData<List<Preis>> getPricesByBeer(String beerId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Preis.COLLECTION)
                .orderBy(Preis.FIELD_CREATION_DATE, Query.Direction.DESCENDING)
                .whereEqualTo(Preis.FIELD_BEER_ID, beerId), Preis.class);
    }

    public LiveData<List<Preis>> getAllPrices() {
        return allPrices;
    }

    public LiveData<List<Preis>> getMyPrices(LiveData<String> currentUserId) {
        return switchMap(currentUserId, PreisRepository::getPricesByUser);
    }

    public LiveData<List<Preis>> getPricesForBeer(LiveData<String> beerId) {
        return switchMap(beerId, PreisRepository::getPricesByBeer);
    }

}