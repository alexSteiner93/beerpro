package ch.beerpro.presentation.details;
import android.util.Log;
import androidx.lifecycle.ViewModel;
import ch.beerpro.data.repositories.CurrentUser;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Price;
import ch.beerpro.presentation.utils.EntityClassSnapshotParser;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Date;

public class CreatePriceViewModel extends ViewModel implements CurrentUser {

    private static final String TAG = "CreatePriceViewModel";

    private EntityClassSnapshotParser<Price> parser = new EntityClassSnapshotParser<>(Price.class);
    private Beer item;

    public Beer getItem() {
        return item;
    }

    public void setItem(Beer item) {
        this.item = item;
    }

    public Task<Price> savePrice(Beer item, float price) {
        String currentUser = getCurrentUser().getUid();

        Price newPrice = new Price(null, item.getId(), currentUser, price, new Date());

        Log.i(TAG, "Adding new price: " + newPrice.toString());
        return FirebaseFirestore.getInstance().collection(Price.COLLECTION).add(newPrice)
                .continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        return task.getResult().get();
                    } else {
                        throw task.getException();
                    }
                })
                .continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        return Tasks.forResult(parser.parseSnapshot(task.getResult()));
                    } else {
                        throw task.getException();
                    }
                });
    }

}