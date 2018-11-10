package br.com.andersonsv.recipe.util;

import android.content.Context;
import android.content.res.Configuration;

import br.com.andersonsv.recipe.R;

public class UiUtils {

    private static final int PHONE_POTRAIT_COLUMN = 1;
    private static final int PHONE_LANDSCAPE_COLUMN = 2;
    private static final int TABLET_POTRAIT_COLUMN = 2;
    private static final int TABLET_LANDSCAPE_COLUMN = 3;

    //code copied from https://github.com/gkj/baking-app
    public static int getColumnForDimension(Context context) {
        boolean isTablet = context.getResources().getBoolean(R.bool.isTablet);
        boolean isLandscape = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isTablet) {
            return isLandscape ? TABLET_LANDSCAPE_COLUMN : TABLET_POTRAIT_COLUMN;
        } else {
            return isLandscape ? PHONE_LANDSCAPE_COLUMN : PHONE_POTRAIT_COLUMN;
        }
    }
}
