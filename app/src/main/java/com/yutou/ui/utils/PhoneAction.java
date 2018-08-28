package com.yutou.ui.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

public class PhoneAction {
    static public String getICCID(Context ct) {
//        String sICCID = "89860073111451096403";
        String sICCID = null;
        TelephonyManager tm = (TelephonyManager) ct
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            if (ActivityCompat.checkSelfPermission(ct, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                String ssn = tm.getSimSerialNumber();
                if (ssn != null && ssn.length() == 20) {
                    sICCID = ssn;
                }
            }
        }
        return sICCID;
    }

    static public String getIMSI(Context ct) {
        String sICCID = "12345678901234567890";
        TelephonyManager tm = (TelephonyManager) ct
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            if (ActivityCompat.checkSelfPermission(ct, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                sICCID = tm.getSubscriberId();
            }

        }
        return sICCID;
    }

    static public String getAndroidId(Context ct) {
        String sAndroidID = "12345678901234567890";
        sAndroidID = Settings.Secure.getString(ct.getContentResolver(), Settings.Secure.ANDROID_ID);
        return sAndroidID;
    }

    static public String getMacAddress(Context ct){
        String result = "";
        WifiManager wifiManager = (WifiManager) ct.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        return result;
    }

}
