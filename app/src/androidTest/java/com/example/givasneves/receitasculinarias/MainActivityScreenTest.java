package com.example.givasneves.receitasculinarias;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
    public void changeOrientation_check() {

        //verifica se a lista foi carregada no recycler view
        onView(new RecyclerViewMatcher(R.id.rv_recipe_list)
                .atPosition(BROWNIE_POSITION)).check(matches(hasDescendant(withText(BROWNIE_TEXT))));

        //rotaciona a tela para testar o stado
        rotateScreen();

        //verifica se a lista foi carregada no recycler view
        new RecyclerViewMatcher(R.id.rv_recipe_list).atPosition(BROWNIE_POSITION)
                .matches(hasDescendant(withText(BROWNIE_TEXT)));

        //devolve a tela para a posição original
        rotateScreen();
    }

    @Test
    public void clickGridViewItem_OpenOrderActivity() {

        //executa o click no segundo item do recyclerview
        onView(withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(BROWNIE_POSITION),
                        click());

        //Verifica se a nova tela aberta contem o item desejado
        ViewMatchers.withId(R.id.recipe_title).matches(withText(BROWNIE_TEXT));

        //rotaciona a tela
        rotateScreen();

        //Verifica se a nova tela aberta contem o item desejado
        ViewMatchers.withId(R.id.recipe_title).matches(withText(BROWNIE_TEXT));

        //rotaciona a tela
        rotateScreen();

    }

    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        MainActivity activity = mActivityTestRule.getActivity();

        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        );
    }

}
