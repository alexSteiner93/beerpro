package ch.beerpro.presentation.details.Price;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import ch.beerpro.R;
import ch.beerpro.presentation.details.DetailsActivity;

public class PriceFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.fragment_price);
        builder.setMessage("Füge deinen Preis ein")
                .setPositiveButton("Ok", (dialog, id) -> {
                    EditText et = getDialog().findViewById(R.id.PriceInput);
                    float price = Float.parseFloat(et.getText().toString());
                    ((DetailsActivity)getActivity()).updatePrice(price);
                })
                .setNegativeButton("Abbrechen", (dialog, id) -> {
                });
        return builder.create();
    }
}