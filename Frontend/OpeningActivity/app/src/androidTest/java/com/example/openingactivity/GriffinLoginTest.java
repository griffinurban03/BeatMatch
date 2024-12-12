package com.example.openingactivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.allOf;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GriffinLoginTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);


    @Test
    public void testLogin() throws InterruptedException {
        Intents.init();
        String emailString = "LAWSON@LAWSON";
        String passwordString = "pass";

        onView(withId(R.id.login_input_username)).perform(typeText(emailString), closeSoftKeyboard());    // Type in testString
        onView(withId(R.id.login_input_password)).perform(typeText(passwordString), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());  // Click on To Second Activity button

        intended( // Check if landed to the second activity and verify extras
                hasComponent(ProfileActivity.class.getName())
        );

        onView(withId(R.id.bottom_navigation)).perform(click());        // Click on To Main Activity button
        intended(hasComponent(SwipingActivity.class.getName()));   // Check if landed to the second activity

        onView(withId(R.id.like)).perform(click());
        onView(withId(R.id.dislike)).perform(click());
        onView(withId(R.id.like)).perform(click());

        Intents.release();
    }

    @Test
    public void testLogin2() throws InterruptedException {
        Intents.init();
        String emailString = "LAWSON@LAWSON";
        String passwordString = "passw";

        onView(withId(R.id.login_input_username)).perform(typeText(emailString), closeSoftKeyboard());    // Type in testString
        onView(withId(R.id.login_input_password)).perform(typeText(passwordString), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());  // Click on To Second Activity button

        Intents.release();
    }

    @Test
    public void testLogin3() throws InterruptedException {
        Intents.init();
        String emailString = "LAWSON@LAW";
        String passwordString = "pass";

        onView(withId(R.id.login_input_username)).perform(typeText(emailString), closeSoftKeyboard());    // Type in testString
        onView(withId(R.id.login_input_password)).perform(typeText(passwordString), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());  // Click on To Second Activity button

        Intents.release();
    }

}
