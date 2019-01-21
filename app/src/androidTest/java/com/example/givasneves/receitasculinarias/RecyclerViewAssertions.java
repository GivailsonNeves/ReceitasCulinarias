package com.example.givasneves.receitasculinarias;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RecyclerViewAssertions {

    /**
     * Provides a RecyclerView assertion based on a view matcher. This allows you to
     * validate whether a RecyclerView contains a row in memory without scrolling the list.
     *
     * @param viewMatcher - an Espresso ViewMatcher for a descendant of any row in the recycler.
     * @return an Espresso ViewAssertion to check against a RecyclerView.
     */
    public static ViewAssertion withRowContaining(final Matcher<View> viewMatcher) {
        assertNotNull(viewMatcher);

        return new ViewAssertion() {

            @Override
            public void check(View view, NoMatchingViewException noViewException) {
                if (noViewException != null) {
                    throw noViewException;
                }

                assertTrue(view instanceof RecyclerView);

                RecyclerView recyclerView = (RecyclerView) view;
                final RecyclerView.Adapter adapter = recyclerView.getAdapter();
                for (int position = 0; position < adapter.getItemCount(); position++) {
                    int itemType = adapter.getItemViewType(position);
                    RecyclerView.ViewHolder viewHolder = adapter.createViewHolder(recyclerView, itemType);
                    adapter.bindViewHolder(viewHolder, position);

                    if (viewHolderMatcher(hasDescendant(viewMatcher)).matches(viewHolder)) {
                        return; // Found a matching row
                    }
                }

                fail("No match found");
            }
        };
    }

    /**
     * Creates matcher for view holder with given item view matcher.
     *
     * @param itemViewMatcher a item view matcher which is used to match item.
     * @return a matcher which matches a view holder containing item matching itemViewMatcher.
     */
    private static Matcher<RecyclerView.ViewHolder> viewHolderMatcher(final Matcher<View> itemViewMatcher) {
        return new TypeSafeMatcher<RecyclerView.ViewHolder>() {

            @Override
            public boolean matchesSafely(RecyclerView.ViewHolder viewHolder) {
                return itemViewMatcher.matches(viewHolder.itemView);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("holder with view: ");
                itemViewMatcher.describeTo(description);
            }
        };
    }
}