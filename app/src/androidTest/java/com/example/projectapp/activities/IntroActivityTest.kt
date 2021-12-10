package com.example.projectapp.activities


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.projectapp.R
import com.example.projectapp.ui.activities.IntroActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class IntroActivityTest {

    @get:Rule
    val activityRule: ActivityScenarioRule<IntroActivity> = ActivityScenarioRule(IntroActivity::class.java) // launch this activity

    /**
     * Checks if this activity launches successfully.
     */
    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.intro)).check(matches(isDisplayed()))
    }

    /**
     * Checks if this button displayed successfully.
     */
    @Test
    fun test_visibility_title_signInButton() {
        onView(withId(R.id.intro_signInButton)).check(matches(isDisplayed()))
    }

    /**
     * Checks if this button displayed successfully.
     */
    @Test
    fun test_visibility_title_signUpButton() {
        onView(withId(R.id.intro_signUpButton)).check(matches(isDisplayed()))
    }

    /**
     * Checks if this textView displayed successfully.
     */
    @Test
    fun test_visibility_title_letsStartTextView(){
        onView(withId(R.id.intro_mainText)).check(matches(isDisplayed()))
    }

    /**
     * Checks if this textView's text is correct.
     */
    @Test
    fun test_isTitleTextDisplayed(){
        onView(withId(R.id.intro_mainText)).check(matches(withText(R.string.intro_main_text)))
    }

    /**
     * Checks if this button's text is correct.
     */
    @Test
    fun test_isSignUpButtonTextDisplayed(){
        onView(withId(R.id.intro_signUpButton)).check(matches(withText("Sign Up")))
    }

    /**
     * Checks if this button's text is correct.
     */
    @Test
    fun test_isSignInButtonTextDisplayed(){
        onView(withId(R.id.intro_signInButton)).check(matches(withText("Sign In")))
    }

    /**
     * Checks if this button action will navigate to the correct activity.
     */
    @Test
    fun test_signUpNavigate(){
        onView(withId(R.id.intro_signUpButton)).perform(click())
        onView(withId(R.id.signUp)).check(matches(isDisplayed()))
    }
}