package com.example.openingactivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GriffinIntentTest {

    @Rule
    public ActivityScenarioRule<StartupActivity> activityScenarioRule = new ActivityScenarioRule<>(StartupActivity.class);


    @Test
    public void testIntent() throws InterruptedException {
        Intents.init();

        onView(withId(R.id.log_in_button)).perform(click());  // Click on To Second Activity button
        intended(hasComponent(LoginActivity.class.getName()));   // Check if landed to the second activity

        String emailString = "LAWSON@LAWSON";
        String passwordString = "pass";

        onView(withId(R.id.login_input_username)).perform(typeText(emailString), closeSoftKeyboard());    // Type in testString
        onView(withId(R.id.login_input_password)).perform(typeText(passwordString), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());  // Click on To Second Activity button

        intended( // Check if landed to the second activity and verify extras
                hasComponent(ProfileActivity.class.getName())
        );

        onView(withId(R.id.swiping)).perform(click());        // Click on To Main Activity button
        intended(hasComponent(SwipingActivity.class.getName()));   // Check if landed to the second activity

        onView(withId(R.id.like)).perform(click());
        onView(withId(R.id.dislike)).perform(click());
        onView(withId(R.id.like)).perform(click());


        onView(withId(R.id.leaderboard)).perform(click());        // Click on To Main Activity button
        intended(hasComponent(LeaderboardActivity.class.getName()));   // Check if landed to the second activity


        onView(withId(R.id.events)).perform(click());        // Click on To Main Activity button
        intended(hasComponent(EventsActivity.class.getName()));   // Check if landed to the second activity


        onView(withId(R.id.friends)).perform(click());        // Click on To Main Activity button
        intended(hasComponent(FriendsActivity.class.getName()));   // Check if landed to the second activity


        onView(withId(R.id.button_find_friends)).perform(click());        // Click on To Main Activity button
        intended(hasComponent(FriendFinderActivity.class.getName()));   // Check if landed to the second activity

        onView(withId(R.id.button_friends_back)).perform(click());        // Click on To Main Activity button
        intended(hasComponent(FriendsActivity.class.getName()));

        onView(withId(R.id.profile)).perform(click());        // Click on To Main Activity button

        Intents.release();
    }

}
