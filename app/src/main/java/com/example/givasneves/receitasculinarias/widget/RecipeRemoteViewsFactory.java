package com.example.givasneves.receitasculinarias.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.givasneves.receitasculinarias.R;
import com.example.givasneves.receitasculinarias.model.Recipe;

public class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Recipe recipe;
    private Context mContext;

    public RecipeRemoteViewsFactory(Context applicationContexto, Intent intent) {
        mContext = applicationContexto;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        final long identityToken = Binder.clearCallingIdentity();
        this.recipe = RecipeWidgetProvider.getRecipe();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        return;
    }

    @Override
    public int getCount() {
        return recipe == null ? 0 : recipe.ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION ||
                recipe == null || position >= recipe.ingredients.size()) {
            return  null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_item);
        rv.setTextViewText(R.id.tv_ingredient_name, recipe.ingredients.get(position).ingredient);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
