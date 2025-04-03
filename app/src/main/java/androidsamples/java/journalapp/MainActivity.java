package androidsamples.java.journalapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import androidsamples.java.journalapp.R;

public class MainActivity extends AppCompatActivity {
  private NavController mNavController;
  private AppBarConfiguration mAppBarConfiguration;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Set up the toolbar if required
    //Toolbar toolbar = findViewById(R.id.toolbar);
    //setSupportActionBar(toolbar);

    // Initialize NavController
    NavHostFragment navHostFragment =
            (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    if (navHostFragment != null) {
      mNavController = navHostFragment.getNavController();

      // Configure AppBar for Up Navigation
      mAppBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
      NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
    }
  }

  // Enable Up navigation with the NavController
  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return NavigationUI.navigateUp(mNavController, mAppBarConfiguration)
            || super.onSupportNavigateUp();
  }
}
