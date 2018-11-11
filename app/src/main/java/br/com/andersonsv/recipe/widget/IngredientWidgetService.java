package br.com.andersonsv.recipe.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import br.com.andersonsv.recipe.R;
import br.com.andersonsv.recipe.data.Ingredient;

public class IngredientWidgetService extends RemoteViewsService {

    private List<Ingredient> ingredients;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteListViewsFactory(getApplicationContext());
    }

    class RemoteListViewsFactory implements IngredientWidgetService.RemoteViewsFactory {

        final Context mContext;

        RemoteListViewsFactory(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            ingredients = IngredientWidgetProvider.ingredients;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (ingredients == null) return 0;
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int index) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_item);
            Ingredient ingredient = ingredients.get(index);
            int position = index + 1;
            String widgetItem = String.format(getString(R.string.widget_item_format), position,
                    ingredient.getIngredient(), Double.toString(ingredient.getQuantity()), ingredient.getMeasure());

            views.setTextViewText(R.id.tvIngredientItem, widgetItem);
            return views;
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
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
