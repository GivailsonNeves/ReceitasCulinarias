package com.example.givasneves.receitasculinarias.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.givasneves.receitasculinarias.MainActivity;
import com.example.givasneves.receitasculinarias.R;
import com.example.givasneves.receitasculinarias.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static  final String RECIPE_PARCEABLE_NAME = "RecipeSelected";
    private static Recipe recipe;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {


        final RemoteViews views = new RemoteViews(
                context.getPackageName(), R.layout.recipe_widget_provider
        );

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.btn_open_app, pendingIntent);


        Intent IntentListView = new Intent(context, RecipeRemoteViewsService.class);
        views.setRemoteAdapter(R.id.widgetListView, IntentListView);


        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    public static Recipe getRecipe() {
        return RecipeWidgetProvider.recipe;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

    public static void sendRefreshBroadcast(Context context, Recipe recipe) {

        RecipeWidgetProvider.recipe = recipe;
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, RecipeWidgetProvider.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, RecipeWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widgetListView);

        }
        super.onReceive(context, intent);
    }
}

