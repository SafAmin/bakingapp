package com.baking.thebaking.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by Safa Amin on 8/26/2018.
 */

public class BakingWidgetUpdateService extends IntentService {

    public static String INGREDIENTS_LIST_PARAM = "INGREDIENTS_LIST";

    public BakingWidgetUpdateService() {
        super("");
    }

    public static void startBakingService(Context context, ArrayList<String> ingredientsList) {
        Intent intent = new Intent(context, BakingWidgetUpdateService.class);
        intent.putExtra(INGREDIENTS_LIST_PARAM, ingredientsList);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            ArrayList<String> ingredientsList = intent.getExtras().getStringArrayList(INGREDIENTS_LIST_PARAM);
            handleActionUpdateBakingWidgets(ingredientsList);
        }
    }

    private void handleActionUpdateBakingWidgets(ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra(INGREDIENTS_LIST_PARAM, fromActivityIngredientsList);
        sendBroadcast(intent);
    }
}
