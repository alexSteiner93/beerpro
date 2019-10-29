package ch.beerpro.presentation.profile.myfridge;

import android.util.Pair;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ch.beerpro.data.repositories.BeersRepository;
import ch.beerpro.data.repositories.CurrentUser;
import ch.beerpro.data.repositories.FridgeRepository;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.FridgeBeer;

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

    public LiveData<List<Pair<FridgeBeer, Beer>>> getMyFridgeWithBeers() {
        return fridgeRepository.getMyFridge(currentUserId, beersRepository.getAllBeers());
    }

    public void addToFridge(FridgeBeer fridgeItem) {
        fridgeRepository.addFridgeBeer(currentUserId.getValue(), fridgeItem.getBeerId());
    }

    public void removeFromFridge(FridgeBeer fridgeItem) {
        fridgeRepository.removeFridgeBeer(currentUserId.getValue(), fridgeItem.getBeerId());
    }

    public void addToFridge(Beer item) {
        fridgeRepository.addFridgeBeer(currentUserId.getValue(), item.getId());
    }

}