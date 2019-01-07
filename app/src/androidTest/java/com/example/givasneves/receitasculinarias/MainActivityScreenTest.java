package com.example.givasneves.receitasculinarias;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import com.example.givasneves.receitasculinarias.adapter.RecipeListAdapter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class, false, false);

    private static final Intent MY_ACTIVITY_INTENT = new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class);

    private static int BROWNIE_POSITION = 1;
    private static String BROWNIE_TEXT = "Brownies";

    @Before
    public void setup() {
        mActivityTestRule.launchActivity(MY_ACTIVITY_INTENT);
    }

    @Test
    public void clickGridViewItem_OpenOrderActivity() {

        //executa o click no segundo item do recyclerview
        onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(BROWNIE_POSITION,
                        click()));

        //Verifica se a nova tela aberta contem o item desejado
        onView(withId(R.id.recipe_title)).check(matches(withText(BROWNIE_TEXT)));


    }

}
