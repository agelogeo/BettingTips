package gr.betting.admin.bettingtips;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

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
