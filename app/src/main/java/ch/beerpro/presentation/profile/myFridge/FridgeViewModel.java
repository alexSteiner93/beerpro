package ch.beerpro.presentation.profile.myFridge;

import android.util.Pair;

import com.google.android.gms.tasks.Task;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.data.repositories.BeersRepository;
import ch.beerpro.data.repositories.CurrentUser;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.FridgeEntry;

public class FridgeViewModel extends ViewModel implements CurrentUser {

    private static final String TAG = "FridgeViewModel";

    private final MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private final FridgeRepository fridgeRepository;
    private final BeersRepository beersRepository;

    public FridgeViewModel() {
        fridgeRepository = new FridgeRepository();
        beersRepository = new BeersRepository();

        currentUserId.setValue(getCurrentUser().getUid());
    }


    public LiveData<List<Pair<FridgeEntry, Beer>>> getMyFridgeWithBeers() {
        return fridgeRepository.getMyFridgeWithBeers(currentUserId, beersRepository.getAllBeers());
    }

    public Task<Void> toggleBeerToFridge(String itemId) {
        return fridgeRepository.toggleUserFridgeItem(getCurrentUser().getUid(), itemId);
    }

}