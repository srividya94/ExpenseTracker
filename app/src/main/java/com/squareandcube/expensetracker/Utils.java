package com.squareandcube.expensetracker;

/**
 * Created by SRI on 04/12/2017.
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;

import java.util.regex.Pattern;

/**
 * Created by PAVAN KUMAR on 10/20/2017.
 */

class Utils {
    public static boolean isValidMobile(String phone) {
        boolean check;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 6 || phone.length() > 13) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static float dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return dp * density;
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static String removeEmptySpace(String s) {
        return s.replaceAll(" ", "");
    }

}
