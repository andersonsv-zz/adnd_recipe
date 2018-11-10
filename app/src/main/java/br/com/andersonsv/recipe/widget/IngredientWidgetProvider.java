package br.com.andersonsv.recipe.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Ingredient;
import br.com.andersonsv.recipe.data.Recipe;

import static br.com.andersonsv.recipe.util.Extras.EXTRA_RECIPE;

public class IngredientWidgetProvider extends AppWidgetProvider {

    static List<Ingredient> ingredients = new ArrayList<>();
    private static String text;

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_provider);
        views.setTextViewText(R.id.tvWidgetTitle, text);

        Intent intent = new Intent(context, IngredientWidgetService.class);
        views.setRemoteAdapter(R.id.lvIngredients, intent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null && intent.hasExtra(EXTRA_RECIPE)) {
            Recipe recipe = intent.getParcelableExtra(EXTRA_RECIPE);
            text = recipe.getName();
            ingredients = recipe.getIngredients();
        } else {
            text = context.getString(R.string.widget_no_recipe_selected);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName widget = new ComponentName(context.getApplicationContext(), IngredientWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lvIngredients);
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            onUpdate(context, appWidgetManager, appWidgetIds);
        }

        super.onReceive(context, intent);
    }
}
