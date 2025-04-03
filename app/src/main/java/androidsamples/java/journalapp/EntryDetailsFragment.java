package androidsamples.java.journalapp;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment {


  private JournalViewModel mJournalViewModel;

  void saveEntry(View view, boolean type){
    EditText titleView = view.findViewById(R.id.edit_title);
    String newTitle = titleView.getText().toString().trim();
    if(!newTitle.isEmpty()) {
      mJournalViewModel.setTitle(titleView.getText().toString());
    }else{
      Toast.makeText(getActivity(),"Invalid Title",Toast.LENGTH_SHORT).show();
    }
    try{
      if(type){
        Log.d("Hmm","Update");
        mJournalViewModel.saveEntry();
        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
      }else{
        Log.d("Hmm","Add");
        mJournalViewModel.insertEntry();
        Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
      }
      Navigation.findNavController(view).popBackStack();
    }catch(Exception e){
      //throw e;
      Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
    }
  }
  void updateUI(View view){
    Date date = mJournalViewModel.getDate();
    Time startTime = mJournalViewModel.getStartTime();
    Time endTime = mJournalViewModel.getEndTime();
    String title = mJournalViewModel.getTitle();
    JournalEntry je = mJournalViewModel.getmJournalEntry();
    EditText editTitle = view.findViewById(R.id.edit_title);
    Button dateButton = view.findViewById(R.id.btn_entry_date);
    Button startTimeButton = view.findViewById(R.id.btn_start_time);
    Button endTimeButton = view.findViewById(R.id.btn_end_time);
    if(title!=null && !title.isEmpty()){
      editTitle.setText(title);
    } else if (je.getmTitle()!=null) {
      editTitle.setText(je.getmTitle());
    }
    if(date!=null){
      dateButton.setText(date.toString());
    } else if (je.getmDate()!=null) {
      dateButton.setText(je.getmDate().toString());
    }
    if(startTime!=null){
      startTimeButton.setText(startTime.toString().substring(0,5));
    } else if (je.getmStartTime()!=null) {
      startTimeButton.setText(je.getmStartTime().toString().substring(0,5));
    }
    if(endTime!=null){
      endTimeButton.setText(endTime.toString().substring(0,5));
    } else if (je.getmEndTime()!=null) {
      endTimeButton.setText(je.getmEndTime().toString().substring(0,5));
    }
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mJournalViewModel = new ViewModelProvider(getActivity()).get(JournalViewModel.class);
    //mJournalViewModel.reset();
    return inflater.inflate(R.layout.fragment_entry_details, container, false);
  }

private void shareEntryDetails() {
    JournalEntry entry = mJournalViewModel.getmJournalEntry(); // Fetch entry details (title, date, start-time, end-time)
    String shareMessage = "Look what I have been up to: " + entry.getmTitle() + " on " + entry.getmDate().toString()
            + ", " + entry.getmStartTime().toString() + " to " + entry.getmEndTime().toString();

    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
    shareIntent.setType("text/plain");

    // Start the chooser to let the user select a sharing method
    startActivity(Intent.createChooser(shareIntent, "Share via"));
}

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
      @Override
      public void handleOnBackPressed() {
        // Handle the back button event
        mJournalViewModel.reset();
        Navigation.findNavController(view).popBackStack();
      }
    };
    requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);
    EntryDetailsFragmentArgs args = EntryDetailsFragmentArgs.fromBundle(getArguments());
    String uuid = args.getEntryId();
    boolean type = args.getType();
    if (type) {
      mJournalViewModel.loadEntry(UUID.fromString(uuid));

      mJournalViewModel.getEntryLiveData().observe(getActivity(),
              entry -> {
                if(entry!=null) {
                  mJournalViewModel.setmJournalEntry(entry);
                  //this.mEntry = entry;
                  //mJournalViewModel.setTitle(entry.getmTitle());
                  //mJournalViewModel.setDate(entry.getmDate());
                  //this.date = mEntry.getmDate();
                  //mJournalViewModel.setStartTime(entry.getmStartTime());
                  //this.endTime = mEntry.getmEndTime();
                  //mJournalViewModel.setEndTime(entry.getmEndTime());
                  updateUI(view);
                }
              });
    }
//    EntryDetailsFragmentArgs args = EntryDetailsFragmentArgs.fromBundle(getArguments());
    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Entry Details");
    requireActivity().addMenuProvider(new MenuProvider() {
      @Override
      public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.fragment_toolbar_menu, menu);
        if(!type){
          changeMenuItemColor(menu.findItem(R.id.action_share), getResources().getColor(R.color.your_color));
        }
      }
      private void changeMenuItemColor(MenuItem item, int color) {
        // Change icon color
        if (item.getIcon() != null) {
          item.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
      }
      @Override
      public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_share) {
          // Handle share action
          if(!type){
            Toast.makeText(getActivity(), "save this first", Toast.LENGTH_SHORT).show();
            return true;
          }
          shareEntryDetails();
          return true;
        } else if (menuItem.getItemId() == R.id.action_garbage) {
          // Handle delete action
          if(!type){
            //mJournalViewModel.reset();
            //Navigation.findNavController(view).popBackStack();
            getActivity().onBackPressed();
            return true;
          }
          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
          builder.setTitle("Delete Item")
                  .setMessage("Are you sure you want to delete this item?")
                  .setPositiveButton("Delete", (dialog, which) -> {
                    // Handle the delete action
                    mJournalViewModel.deleteEntry(mJournalViewModel.getmJournalEntry());
                    getActivity().onBackPressed();
                    //Navigation.findNavController(view).popBackStack();
                  })
                  .setNegativeButton("Cancel", (dialog, which) -> {
                    // User cancelled the dialog
                    dialog.dismiss();
                  })
                  .setCancelable(false) // Optional: Prevent closing by tapping outside
                  .show();
          return true;
        }
        return false;
      }
    }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

    view.findViewById(R.id.btn_entry_date).setOnClickListener(v->{
      //Log.d(TAG, "moving");
      NavDirections a = EntryDetailsFragmentDirections.datePickerAction(type,type?mJournalViewModel.getDate().getTime():0);
      Navigation.findNavController(view).navigate(a);
    });
    view.findViewById(R.id.btn_start_time).setOnClickListener(v->{
      //Navigation.findNavController(view).getCurrentBackStackEntry().getSavedStateHandle().set("timeType", "start");
      NavDirections a = EntryDetailsFragmentDirections.timePickerAction(type,type?mJournalViewModel.getStartTime().getTime():0,true);
      Navigation.findNavController(view).navigate(a);
    });
    view.findViewById(R.id.btn_end_time).setOnClickListener(v->{
     // Navigation.findNavController(view).getCurrentBackStackEntry().getSavedStateHandle().set("timeType", "end");
      NavDirections a = EntryDetailsFragmentDirections.timePickerAction(type,type?mJournalViewModel.getEndTime().getTime():0,false);
      Navigation.findNavController(view).navigate(a);
    });
    Navigation.findNavController(view).getCurrentBackStackEntry().getSavedStateHandle()
            .getLiveData("selectedDate").observe(getViewLifecycleOwner(), dateInMillis -> {
              // Update JournalViewModel or UI with selected date
              Log.d("Hello","date Heard");
              Button date = view.findViewById(R.id.btn_entry_date);
              Date dateSet = new Date((Long)dateInMillis);
              date.setText(dateSet.toString());
              mJournalViewModel.setDate(dateSet);
            });
    view.findViewById(R.id.btn_save).setOnClickListener(v -> {
      saveEntry(view,type);
    });
    Navigation.findNavController(view).getCurrentBackStackEntry().getSavedStateHandle()
            .getLiveData("startTime").observe(getViewLifecycleOwner(), timeInMillis -> {
              Log.d("Hello","start Heard");
              // Update JournalViewModel or UI with selected date
              Button start = view.findViewById(R.id.btn_start_time);
              Time startSet = new Time((Long)timeInMillis);
              start.setText(startSet.toString().substring(0,5));
              mJournalViewModel.setStartTime(startSet);
            });
    Navigation.findNavController(view).getCurrentBackStackEntry().getSavedStateHandle()
            .getLiveData("endTime").observe(getViewLifecycleOwner(), timeInMillis -> {
              Log.d("Hello","end Heard");
              // Update JournalViewModel or UI with selected date
              Button end = view.findViewById(R.id.btn_end_time);
              Time endSet = new Time((Long)timeInMillis);
              end.setText(endSet.toString().substring(0,5));
              mJournalViewModel.setEndTime(endSet);
            });
  }
}