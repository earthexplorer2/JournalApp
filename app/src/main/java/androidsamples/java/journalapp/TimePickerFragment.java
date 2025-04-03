package androidsamples.java.journalapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    // Set the calendar with the provided time
    TimePickerFragmentArgs args = TimePickerFragmentArgs.fromBundle(getArguments());
    boolean update = args.getAddOrUpdate();
    Calendar calendar = Calendar.getInstance();

    if(update) calendar.setTimeInMillis(args.getDate());
    int hour = calendar.get(Calendar.HOUR);
    int min = calendar.get(Calendar.MINUTE);
    return new TimePickerDialog(getActivity(), (view, hourOfDay, minuteOfHour) -> {
      calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
      calendar.set(Calendar.MINUTE, minuteOfHour);
      long selectedTime = calendar.getTimeInMillis();

      NavController navController = NavHostFragment.findNavController(TimePickerFragment.this);
      //String timeType = navController.getPreviousBackStackEntry().getSavedStateHandle().get("timeType");

      if (args.getStartOrEnd()) {
        navController.getPreviousBackStackEntry().getSavedStateHandle().set("startTime", selectedTime);
      } else if (!args.getStartOrEnd()) {
        navController.getPreviousBackStackEntry().getSavedStateHandle().set("endTime", selectedTime);
      }
      navController.navigateUp();
    }, hour, min, true);
  }
}
