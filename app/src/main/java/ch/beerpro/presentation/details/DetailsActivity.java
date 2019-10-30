package ch.beerpro.presentation.details;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;

import ch.beerpro.presentation.details.Price.PriceFragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.fragment.app.DialogFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.beerpro.GlideApp;
import ch.beerpro.R;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.domain.models.Wish;
import ch.beerpro.presentation.details.createrating.CreateRatingActivity;
import ch.beerpro.presentation.details.improvement.improvementFragment;

import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

import static ch.beerpro.presentation.utils.DrawableHelpers.setDrawableTint;

public class DetailsActivity extends AppCompatActivity implements OnRatingLikedListener {

    public static final String ITEM_ID = "item_id";
    private static final String TAG = "DetailsActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.photo)
    ImageView photo;

    @BindView(R.id.avgRating)
    TextView avgRating;

    @BindView(R.id.numRatings)
    TextView numRatings;

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.wishlist)
    ToggleButton wishlist;

    @BindView(R.id.avgPrice)
    TextView avgPrice;

    @BindView(R.id.manufacturer)
    TextView manufacturer;

    @BindView(R.id.category)
    TextView category;

    @BindView(R.id.addRatingBar)
    RatingBar addRatingBar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.noteView)
    CardView noteView;

    @BindView(R.id.noteText)
    EditText noteText;


    private RatingsRecyclerViewAdapter adapter;

    private DetailsViewModel model;
    private String itemid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        toolbar.setTitleTextColor(Color.alpha(0));

        String beerId = getIntent().getExtras().getString(ITEM_ID);

        model = ViewModelProviders.of(this).get(DetailsViewModel.class);
        model.setBeerId(beerId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RatingsRecyclerViewAdapter(this, model.getCurrentUser());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        model.getBeer().observe(this, this::updateBeer);
        model.getRatings().observe(this, this::updateRatings);
        model.getWish().observe(this, this::toggleWishlistView);

        recyclerView.setAdapter(adapter);
        SharedPreferences notePref = getSharedPreferences("Note", MODE_PRIVATE);
        noteText.setText(notePref.getString(itemid, ""));
        addRatingBar.setOnRatingBarChangeListener(this::addNewRating);

    }

    private void addNewRating(RatingBar ratingBar, float v, boolean b) {
        Intent intent = new Intent(this, CreateRatingActivity.class);
        intent.putExtra(CreateRatingActivity.ITEM, model.getBeer().getValue());
        intent.putExtra(CreateRatingActivity.RATING, v);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, addRatingBar, "rating");
        startActivity(intent, options.toBundle());
    }

    @OnClick(R.id.actionsButton)
    public void showBottomSheetDialog() {
        View view = getLayoutInflater().inflate(R.layout.single_bottom_sheet_dialog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        view.findViewById(R.id.addPrice).setOnClickListener(v -> {
            DialogFragment newFragment = new PriceFragment();
            newFragment.show(getSupportFragmentManager(), "missiles");

        });

        view.findViewById(R.id.improvementButton).setOnClickListener(v -> {
            DialogFragment newFragment = new improvementFragment();
            newFragment.show(getSupportFragmentManager(), "missiles");

        });

        view.findViewById(R.id.addPrivateNote).setOnClickListener(v -> {

            EditText noteText = new EditText(view.getContext());
            SharedPreferences notePref = getSharedPreferences("Note", MODE_PRIVATE);
            noteText.setText(notePref.getString(itemid, ""));
            new AlertDialog.Builder(view.getContext()).setTitle("Ihre Persönliche Notiz").setView(noteText)
                    .setPositiveButton(android.R.string.yes, (dialo, which) -> {
                        SharedPreferences.Editor noteEditor = notePref.edit();
                        noteEditor.putString(itemid, noteText.getText().toString());
                        noteEditor.apply();
                        noteText.setText(notePref.getString(itemid, ""));
                    }).setNegativeButton(android.R.string.no, null).show();
        });

        view.findViewById(R.id.addToFridge).setOnClickListener(v -> onFridgeClickedListener(v));

        dialog.show();
    }

    private void updateBeer(Beer item) {
        name.setText(item.getName());
        manufacturer.setText(item.getManufacturer());
        category.setText(item.getCategory());
        name.setText(item.getName());
        GlideApp.with(this).load(item.getPhoto()).apply(new RequestOptions().override(120, 160).centerInside())
                .into(photo);
        ratingBar.setNumStars(5);
        ratingBar.setRating(item.getAvgRating());
        avgRating.setText(getResources().getString(R.string.fmt_avg_rating, item.getAvgRating()));
        numRatings.setText(getResources().getString(R.string.fmt_ratings, item.getNumRatings()));
        toolbar.setTitle(item.getName());
        if (item.getNumberOfPrices() == 0) { avgPrice.setText("Kein Preis Vorhanden");}
        else {
            avgPrice.setText("Preis: von " + item.getMinimumPrice() + " bis " + item.getMaximumPrice() +
                    ", Durchschnitt: " + item.getAvgPrice());
        }
    }

    private void updateRatings(List<Rating> ratings) {
        adapter.submitList(new ArrayList<>(ratings));
    }

    @Override
    public void onRatingLikedListener(Rating rating) {
        model.toggleLike(rating);
    }

    @OnClick(R.id.wishlist)
    public void onWishClickedListener(View view) {
        model.toggleItemInWishlist(model.getBeer().getValue().getId());
        /*
         * We won't get an update from firestore when the wish is removed, so we need to reset the UI state ourselves.
         * */
        if (!wishlist.isChecked()) {
            Toast.makeText(DetailsActivity.this, "Das Bier wurde von der Wunschliste entfernt", Toast.LENGTH_SHORT).show();
            toggleWishlistView(null);
        } else {
            Toast.makeText(DetailsActivity.this, "Das Bier wurde zur Wunschliste hinzugefügt.", Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleWishlistView(Wish wish) {
        if (wish != null) {
            int color = getResources().getColor(R.color.colorPrimary);
            setDrawableTint(wishlist, color);
            wishlist.setChecked(true);
        } else {
            int color = getResources().getColor(android.R.color.darker_gray);
            setDrawableTint(wishlist, color);
            wishlist.setChecked(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sendImprovement(String text){

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"alexander.steiner@hsr.ch"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Improvement");
        i.putExtra(Intent.EXTRA_TEXT   , text);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
        }

    }


    public void updatePrice(float priceInput) {
        model.updateBeerPrice(priceInput);
    }

    public void onFridgeClickedListener(View view) {
        model.addToFridge(model.getBeer().getValue().getId());
        Toast.makeText(DetailsActivity.this, "Das Bier wurde zum Kühlschrank hinzugefügt.", Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.shareButton)
    public void onShareBeerClickedListener(View view) {
        Intent sendIntent = new Intent();
        String currentBeer = model.getBeer().getValue().getId();
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https").authority("beerpro.page.link").appendPath("beerdetails")
                .appendQueryParameter("currentBeer", currentBeer);

        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink().setLink(builder.build())
                .setDomainUriPrefix("beerpro.page.link").setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Link:" + dynamicLinkUri.toString());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, ""));
    }


}