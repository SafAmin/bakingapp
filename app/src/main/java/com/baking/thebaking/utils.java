package com.baking.thebaking;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Safa Amin on 7/22/2018.
 */

public class utils {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        return (int) (dpWidth / 180);
    }
}
