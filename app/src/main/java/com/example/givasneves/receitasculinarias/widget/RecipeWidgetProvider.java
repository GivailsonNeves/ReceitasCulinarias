package com.example.givasneves.receitasculinarias.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.givasneves.receitasculinarias.MainActivity;
import com.example.givasneves.receitasculinarias.R;
import com.example.givasneves.receitasculinarias.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static final String RECIPE_SELECTED = "com.example.givasneves.receitasculinarias.RECIPE_SELECTED";
    public static  final String RECIPE_PARCEABLE_NAME = "RecipSelected";
    private static Recipe recipe;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Recipe _recipe, int appWidgetId) {
        recipe = _recipe;

        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.btn_open_app, pendingIntent);

        if(recipe != null) {
            views.setTextViewText(R.id.tv_recipe_name, recipe.name);
            views.setTextViewText(R.id.tv_servings, "Serve " + recipe.servings);
            views.setTextViewText(R.id.tv_recipe_itens, Html.fromHtml(recipe.getIngredientsHtml()));

        }

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
//            new RemoteViews(
//                    context.getPackageName(),
//                    R.layout.collection_wiget
//            )
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager,
                                    Recipe recipe, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(RECIPE_SELECTED)) {
            recipe = (Recipe) intent.getParcelableExtra(RECIPE_PARCEABLE_NAME);
            Log.i(RecipeWidgetProvider.class.getName(), recipe.name);

        }
    }
}

