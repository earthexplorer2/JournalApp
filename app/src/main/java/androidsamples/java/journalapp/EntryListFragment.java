package androidsamples.java.journalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {
  private JournalViewModel mEntryListViewModel;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mEntryListViewModel = new ViewModelProvider(this)
            .get(JournalViewModel.class);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_entry_list, container, false);

    return view;
  }



  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    RecyclerView entriesList = view.findViewById(R.id.recyclerView);
    entriesList.setLayoutManager(new LinearLayoutManager(getActivity()));

    // Set up the adapter with an OnItemClickListener
    JournalEntryListAdapter adapter = new JournalEntryListAdapter(getActivity(), entry -> {
      // Navigate to entry details when an item is clicked
      mEntryListViewModel.reset();
      //reset();
      NavDirections action = EntryListFragmentDirections.viewEntryAction(true,entry.getmUid().toString());
      Navigation.findNavController(view).navigate(action);
    });
    entriesList.setAdapter(adapter);

    mEntryListViewModel.getAllEntries().observe(getViewLifecycleOwner(), adapter::setEntries);
    view.findViewById(R.id.btn_add_entry).setOnClickListener(v->{
      mEntryListViewModel.reset();
      //reset();
      NavDirections a = EntryListFragmentDirections.addEntryAction(false);
      Navigation.findNavController(view).navigate(a);
    });
    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Entry List");
    requireActivity().addMenuProvider(new MenuProvider() {
      @Override
      public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.fragment_toolbar_menu_2, menu);
      }

      @Override
      public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_info) {
          // Handle share action
          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jamesclear.com/atomic-habits"));
          startActivity(intent);
          return true;
        }
        return false;
      }
    }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

  }

}