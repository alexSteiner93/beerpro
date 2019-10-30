package ch.beerpro.presentation.details.improvement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import ch.beerpro.R;
import ch.beerpro.presentation.details.DetailsActivity;

public class improvementFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.fragment_improvement);
        builder.setMessage("Teile uns deine Verbesserug mit oder melde eine Fehler")
                .setPositiveButton("Ok", (dialog, id) -> {
                    EditText et = getDialog().findViewById(R.id.ImprovementInput);
                    String userText = et.getText().toString();
                    ((DetailsActivity)getActivity()).sendImprovement(userText);
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}