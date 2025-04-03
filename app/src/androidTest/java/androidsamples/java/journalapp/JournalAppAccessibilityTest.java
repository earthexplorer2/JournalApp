package androidsamples.java.journalapp;

import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class JournalAppAccessibilityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        Log.d("TestSetup", "Setting up the test environment");
    }



    //Working
    @Test
    public void testEntryPersistence() {
        // Add a new entry
        Espresso.onView(ViewMatchers.withId(R.id.btn_add_entry))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edit_title))
                .perform(ViewActions.typeText("Persistent Entry"));
        Espresso.onView(ViewMatchers.withId(R.id.btn_save))
                .perform(ViewActions.click());

        // Restart the activity
        activityRule.getScenario().recreate();

        // Check if the entry is still present
        Espresso.onView(ViewMatchers.withText("Persistent Entry"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    @Test
    public void testEntryTitleEditText() {

        Espresso.onView(ViewMatchers.withId(R.id.btn_add_entry))
                .perform(ViewActions.click());

        // Enter text into the entry title edit text
        Espresso.onView(ViewMatchers.withId(R.id.edit_title))
                .perform(ViewActions.typeText("Test Entry"));

        // Check if the text is displayed in the entry title text view
        Espresso.onView(ViewMatchers.withId(R.id.edit_title))
                .check(ViewAssertions.matches(ViewMatchers.withText("Test Entry")));
    }

    @Test
    public void testEntryDescriptionEditText() {

        Espresso.onView(ViewMatchers.withId(R.id.btn_add_entry))
                .perform(ViewActions.click());

        // Enter text into the entry description edit text
        Espresso.onView(ViewMatchers.withId(R.id.edit_title))
                .perform(ViewActions.typeText("This is a test entry"));

        // Check if the text is displayed in the entry description text view
        Espresso.onView(ViewMatchers.withId(R.id.edit_title))
                .check(ViewAssertions.matches(ViewMatchers.withText("This is a test entry")));
    }

    @Test
    public void testAddEntryButtonVisibility() {
        // Check if the add entry button is visible
        Espresso.onView(ViewMatchers.withId(R.id.btn_add_entry))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testRecyclerViewVisibility() {
        // Check if the entry list is visible
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testInfoButtonVisibility() {
        // Check if the info button is visible
        Espresso.onView(ViewMatchers.withId(R.id.action_info))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @After
    public void tearDown() {
        Log.d("TestSetup", "Tearing down the test environment");
    }

}