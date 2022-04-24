package com.example.deliveryapp;
import com.example.schedulingApp.Activities.MainActivity;
import com.example.schedulingApp.Activities.ReportActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class MainPageTest {
    @Test
    public void launchActivity(){
       //dog page
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.dogsButton)).perform(click());
        onView(withId(R.id.dogListPage)).check(matches(isDisplayed()));
       //owner page
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.ownersButton)).perform(click());
        onView(withId(R.id.ownerListPage)).check(matches(isDisplayed()));
        //services
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.serviceButton)).perform(click());
        onView(withId(R.id.serviceListPage)).check(matches(isDisplayed()));
        //credentials
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.credentialListPage)).check(matches(isDisplayed()));
        //employee
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.employeeButton)).perform(click());
        onView(withId(R.id.employeeListPage)).check(matches(isDisplayed()));
        //reports
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.reportButton)).perform(click());
        onView(withId(R.id.reportMainPage)).check(matches(isDisplayed()));
    }

    @Test
    public void launchReportActivity(){
        //By Month page
        ActivityScenario.launch(ReportActivity.class);
        onView(withId(R.id.serviceByMonthButton)).perform(click());
        onView(withId(R.id.reportByMonthPage)).check(matches(isDisplayed()));
        //byEmployee
        ActivityScenario.launch(ReportActivity.class);
        onView(withId(R.id.servicesByEmployeeButton)).perform(click());
        onView(withId(R.id.reportByEmployeePage)).check(matches(isDisplayed()));
    }
}
