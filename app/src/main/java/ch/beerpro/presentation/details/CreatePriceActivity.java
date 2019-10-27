package ch.beerpro.presentation.details;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.R;
import ch.beerpro.domain.models.Beer;

public class CreatePriceActivity extends AppCompatActivity {

    private static final String TAG = "CreateRatingActivity";

    public static final String ITEM = "item";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.price)
    EditText priceText;

    private CreatePriceViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Beer item = (Beer) getIntent().getExtras().getSerializable(ITEM);

        model = ViewModelProviders.of(this).get(CreatePriceViewModel.class);
        model.setItem(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_rating_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                savePrice();
                return true;
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void savePrice() {
        final String rawPrice = priceText.getText().toString();
        final float p = Float.parseFloat(rawPrice);

        model.savePrice(model.getItem(), p)
                .addOnSuccessListener(task -> onBackPressed())
                .addOnFailureListener(error -> Log.e(TAG, "Could not save notice", error));
    }
}