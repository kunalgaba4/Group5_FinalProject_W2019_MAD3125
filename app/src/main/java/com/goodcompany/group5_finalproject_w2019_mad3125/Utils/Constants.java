package com.goodcompany.group5_finalproject_w2019_mad3125.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public class Constants {
    public static int sHeight;
    public static int sWidth;

    public static boolean checkPermissionForPhoneState(Context context) {
        if (context != null) {
            int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
