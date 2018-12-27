package com.example.givasneves.receitasculinarias;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import com.example.givasneves.receitasculinarias.adapter.RecipeListAdapter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickGridViewItem_OpenOrderActivity() {

        // gridview item and clicks it.
        onData(instanceOf(RecipeListAdapter.class)).inAdapterView(withId(R.id.recipe_list_fragment)).atPosition(1).perform(click());


        // Verifica se ao entrar na tela o campo de titulo foi preenchido.
       // onView(withId(R.id.recipe_title)).check(matches(not(withText(""))));
    }

}
