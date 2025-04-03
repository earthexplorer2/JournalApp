package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    DatePickerFragmentArgs args = DatePickerFragmentArgs.fromBundle(getArguments());
    boolean update = args.getAddOrUpdate();
    Calendar calendar = Calendar.getInstance();
    if (update) calendar.setTimeInMillis(args.getDate());
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    return new DatePickerDialog(getActivity(), (view, year1, month1, dayOfMonth) -> {
      calendar.set(year1, month1, dayOfMonth);
      long selectedDate = calendar.getTimeInMillis();

      NavController navController = NavHostFragment.findNavController(DatePickerFragment.this);
      navController.getPreviousBackStackEntry().getSavedStateHandle().set("selectedDate", selectedDate);
      navController.navigateUp();
    }, year, month, day);
  }
}
