package com.rovaindu.homeservice.utils.timeline;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {

    public static int dpToPx(float dp, Context context) {
        return dpToPx(dp, context.getResources());
    }

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
    public static float dpToPixel(float dp, Resources resources) {
        float density = resources.getDisplayMetrics().density;
        float pixel = dp * density;
        return pixel;
    }
}