package com.kyo.homework;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kyo.homework.ui.moments.MomentsActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.EasyMock2Matchers.equalTo;

/**
 * Created by jianghui on 2017/11/30.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MomentsActivityInstrumentedTest {

    private static final long SWIP_INTERVAL = 2000;

    private IdlingResource idlingresource;

    @Rule
    public ActivityTestRule<MomentsActivity> activityRule = new ActivityTestRule<>(
            MomentsActivity.class);

    @Before
    public void setUp() throws Exception {
        idlingresource = activityRule.getActivity().getCountingIdlingResource();
        Espresso.registerIdlingResources(idlingresource);
    }


    @Test
    public void testUserLoad() {
        onView(withText("John Smith")).check(matches(isDisplayed()));
    }

    @Test
    public void testPage1Load() {
        // page 1
        onView(withText("沙发！")).check(matches(isDisplayed()));
        onView(withText("Joe Portman")).check(matches(isDisplayed()));
        onView(withText("Super hero:Good.\nDoggy Over:Like it too")).check(matches(isDisplayed()));
        onView(withText("Coolzzz")).check(matches(isDisplayed()));
        onView(withText(PAGE_FIRST_ITEM)).check(doesNotExist());
    }

    @Test
    public void testPage2Load() {
        // page 2
        onView(withId(R.id.recycler_view)).perform(swipeUp());
        onView(withText(PAGE_FIRST_ITEM)).check(matches(isDisplayed()));
        onView(withText("Rebecca")).check(doesNotExist());
    }

    @Test
    public void testPage3Load() {
        // page 2
        onView(withId(R.id.recycler_view)).perform(swipeUp());
        waitForPageRefresh();
        onView(withText(PAGE_FIRST_ITEM)).check(matches(isDisplayed()));
        onView(withText("Rebecca")).check(doesNotExist());


        // page 3
        onView(withId(R.id.recycler_view)).perform(swipeUp());
        waitForPageRefresh();
        onView(withText("Rebecca")).check(matches(isDisplayed()));
        onView(withText("第10条！")).check(doesNotExist());
    }

    @Test
    public void testPage4Load() {
        // page 2
        onView(withId(R.id.recycler_view)).perform(swipeUp());
        waitForPageRefresh();
        onView(withText(PAGE_FIRST_ITEM)).check(matches(isDisplayed()));
        onView(withText("Rebecca")).check(doesNotExist());

        // page 3
        onView(withId(R.id.recycler_view)).perform(swipeUp());
        waitForPageRefresh();
        onView(withText("Rebecca")).check(matches(isDisplayed()));
        onView(withText("第10条！")).check(doesNotExist());

        // page 4
        onView(withId(R.id.recycler_view)).perform(swipeUp());
        waitForPageRefresh();
        onView(withText("第10条！")).check(matches(isDisplayed()));
        onView(withText("Louis")).check(doesNotExist());
    }


    @Test
    public void testPage5Load() {
        // page 2
        onView(withId(R.id.recycler_view)).perform(swipeUp());
        waitForPageRefresh();
        onView(withText(PAGE_FIRST_ITEM)).check(matches(isDisplayed()));
        onView(withText("Rebecca")).check(doesNotExist());

        // page 3
        onView(withId(R.id.recycler_view)).perform(swipeUp());
        waitForPageRefresh();
        onView(withText("Rebecca")).check(matches(isDisplayed()));
        onView(withText("第10条！")).check(doesNotExist());

        // page 4
        onView(withId(R.id.recycler_view)).perform(swipeUp());
        waitForPageRefresh();
        onView(withText("第10条！")).check(matches(isDisplayed()));
        onView(withText("Louis")).check(doesNotExist());

        // page 5
        onView(withId(R.id.recycler_view)).perform(swipeUp());
        waitForPageRefresh();
        onView(withText("Louis")).check(matches(isDisplayed()));
    }

    private void waitForPageRefresh(){
        try {
            Thread.sleep(SWIP_INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPullRefresh() {
        // refresh
        onView(withId(R.id.recycler_view)).perform(swipeUp());
        onView(withId(R.id.recycler_view)).perform(swipeDown());
        onView(withText("沙发！")).check(matches(isDisplayed()));
        onView(withText(PAGE_FIRST_ITEM)).check(doesNotExist());
    }

    @After
    public void tearDown() throws Exception {
        Espresso.unregisterIdlingResources(idlingresource);
    }

    public static Matcher<Object> withItemContent(String expectedText) {
        checkNotNull(expectedText);
        return withItemContent(equalTo(expectedText));
    }

    private static final String PAGE_FIRST_ITEM = "Unlike many proprietary big data processing platform different, Spark is built on a unified abstract RDD, making it possible to deal with different ways consistent with large data processing scenarios, including MapReduce, Streaming, SQL, Machine Learning and Graph so on. To understand the Spark, you have to understand the RDD. ";
}
