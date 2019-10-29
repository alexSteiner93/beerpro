
package ch.beerpro.presentation.profile.myFridge;

import android.widget.ImageView;

import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.FridgeBeer;

public interface OnFridgeItemInteractionListener {

    void onMoreClickedListener(ImageView photo, Beer beer);
    void onFridgeAddClickedListener(FridgeBeer fridgeBeer);
    void onFridgeRemoveClickedListener(FridgeBeer fridgeBeer);
}