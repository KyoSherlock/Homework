package com.kyo.homework;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
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
import static android.support.test.espresso.action.ViewActions.swipeUp;
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
    public void testListInitLoad() {
        onData(withItemContent("沙发！")).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        Espresso.unregisterIdlingResources(idlingresource);
    }

    public static Matcher<Object> withItemContent(String expectedText) {
        checkNotNull(expectedText);
        return withItemContent(equalTo(expectedText));
    }
}
