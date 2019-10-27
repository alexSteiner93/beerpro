package ch.beerpro.presentation.profile.myFridge;

import android.widget.ImageView;

import ch.beerpro.domain.models.Beer;

public interface OnFridgeItemInteractionListener {

    void onMoreClickedListener(ImageView photo, Beer beer);

    void onFridgeClickedListener(Beer beer);
}