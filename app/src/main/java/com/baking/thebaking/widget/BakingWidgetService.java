package com.baking.thebaking.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.baking.thebaking.R;

import java.util.List;

import static com.baking.thebaking.widget.BakingWidgetProvider.ingredientsList;

/**
 * Created by Safa Amin on 8/26/2018.
 */

public class BakingWidgetService extends RemoteViewsService {

    List<String> remoteViewIngredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext = null;

        public GridRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            remoteViewIngredientsList = ingredientsList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (remoteViewIngredientsList != null) {
                return remoteViewIngredientsList.size();
            } else {
                return 0;
            }
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredients_item_view);
            views.setTextViewText(R.id.widget_ingredient_name, remoteViewIngredientsList.get(position));
            Intent fillInIntent = new Intent();
            views.setOnClickFillInIntent(R.id.widget_ingredient_name, fillInIntent);

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
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
