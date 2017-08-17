package gr.betting.admin.bettingtips;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.android.vending.billing.IInAppBillingService;
import com.facebook.ads.AdView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Admin on 27/6/2017.
 */

public class  CallHolder {
    private static String standard_new;
    private static String standard_old;
    private static String tameiarxis_new;
    private static String tameiarxis_old;
    private static String bonus_new;
    private static String bonus_old;
    private static String stats;
    private static AdView adView;
    private static Calendar calendar;
    private static String message ;
    private static Boolean isSubscriber;
    private static SharedPreferences app_preferences;
    private static  ArrayList<String> inappresponseList;
    private static  ArrayList<String> subsresponseList;
    private static IInAppBillingService mService;

    public static Boolean getIsSubscriber() {
        return isSubscriber;
    }

    public static void setIsSubscriber(Boolean isSubscriber) {
        CallHolder.isSubscriber = isSubscriber;
    }

    public static IInAppBillingService getmService() {
        return mService;
    }

    public static void setmService(IInAppBillingService mService) {
        CallHolder.mService = mService;
    }

    public static ArrayList<String> getInappresponseList() {
        return inappresponseList;
    }

    public static void setInappresponseList(ArrayList<String> inappresponseList) {
        CallHolder.inappresponseList = inappresponseList;
    }

    public static ArrayList<String> getSubsresponseList() {
        return subsresponseList;
    }

    public static void setSubsresponseList(ArrayList<String> subsresponseList) {
        CallHolder.subsresponseList = subsresponseList;
    }

    public static SharedPreferences getApp_preferences() {
        return app_preferences;
    }

    public static void setApp_preferences(SharedPreferences app_preferences) {
        CallHolder.app_preferences = app_preferences;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        CallHolder.message = message;
    }

    public static Calendar getCalendar() {
        return calendar;
    }

    public static void setCalendar(Calendar calendar) {
        CallHolder.calendar = calendar;
    }

    public static AdView getAdView() {
        return adView;
    }

    public static void setAdView(AdView adView) {
        CallHolder.adView = adView;
    }

    public static String getStats() {
        return stats;
    }

    public static void setStats(String stats) {
        CallHolder.stats = stats;
    }

    public static String getStandard_new() {
        return standard_new;
    }

    public static void setStandard_new(String standard_new) {
        CallHolder.standard_new = standard_new;
    }

    public static String getStandard_old() {
        return standard_old;
    }

    public static void setStandard_old(String standard_old) {
        CallHolder.standard_old = standard_old;
    }

    public static String getTameiarxis_new() {
        return tameiarxis_new;
    }

    public static void setTameiarxis_new(String tameiarxis_new) {
        CallHolder.tameiarxis_new = tameiarxis_new;
    }

    public static String getTameiarxis_old() {
        return tameiarxis_old;
    }

    public static void setTameiarxis_old(String tameiarxis_old) {
        CallHolder.tameiarxis_old = tameiarxis_old;
    }

    public static String getBonus_new() {
        return bonus_new;
    }

    public static void setBonus_new(String bonus_new) {
        CallHolder.bonus_new = bonus_new;
    }

    public static String getBonus_old() {
        return bonus_old;
    }

    public static void setBonus_old(String bonus_old) {
        CallHolder.bonus_old = bonus_old;
    }

    public CallHolder() {
    }
}
