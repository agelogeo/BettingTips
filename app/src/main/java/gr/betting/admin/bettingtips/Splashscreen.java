package gr.betting.admin.bettingtips;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class Splashscreen extends Activity {
    public static Activity splash;
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        splash = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        CallHolder.setAdView(new AdView(getApplicationContext(), getString(R.string.today_banner), AdSize.BANNER_320_50));
        CallHolder.setCalendar(Calendar.getInstance());
        StartAnimations();
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        System.out.println("----------------------------------------------------------------/ Start : "+System.currentTimeMillis());


        //getStats();

        getAltToday();
        getStandardToday(); // Contains SplashScreen.finish
        getBonusToday();
        getStandardHistory();
        getAltHistory();
        getBonusHistory();


    }





    public void getAltToday(){
        //Tameiarxis Today
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
            }

            protected String doInBackground(Void... urls) {
                try {
                    String SheetID = getString(R.string.sheet_id);
                    String SheetName = getString(R.string.tameiarxis_today);
                    String link = "https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=" + SheetID + "&sheet=" + SheetName;


                    System.out.println(link);

                    URL url = new URL(link);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();

                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    System.out.println("ERROR : doInBackground");
                    //loadingDialog.dismiss();
                    Splashscreen.this.finish();
                    return null;
                }
            }

            protected void onPostExecute(final String response) {
                CallHolder.setTameiarxis_new(response);
            }
        }.execute();
    }

    public void getBonusToday(){
        //Bonus Today
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
            }

            protected String doInBackground(Void... urls) {
                try {
                    String SheetID = getString(R.string.sheet_id);
                    ;
                    String SheetName = getString(R.string.andriko_today);
                    String link = "https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=" + SheetID + "&sheet=" + SheetName;


                    System.out.println(link);

                    URL url = new URL(link);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();

                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    System.out.println("ERROR : doInBackground");
                    //loadingDialog.dismiss();
                    Splashscreen.this.finish();
                    return null;
                }
            }

            protected void onPostExecute(final String response) {
                CallHolder.setBonus_new(response);
            }
        }.execute();
    }

    public void getStandardToday(){
        //Standard Today
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
            }

            protected String doInBackground(Void... urls) {
                try {
                    String SheetID = getString(R.string.sheet_id);
                    String SheetName = getString(R.string.standard_today);
                    String link = "https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id="+SheetID+"&sheet="+SheetName;



                    System.out.println(link);

                    URL url = new URL(link);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();

                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    System.out.println("ERROR : doInBackground");
                    //loadingDialog.dismiss();
                    Splashscreen.this.finish();
                    return null;
                }
            }
            protected void onPostExecute(final String response) {
                CallHolder.setStandard_new(response);
                Intent intent = new Intent(Splashscreen.this,
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                Splashscreen.this.finish();
                System.out.println("----------------------------------------------------------------/ End : "+System.currentTimeMillis());
            }
        }.execute();
    }

    public void getStats(){
        //Stats Fragment
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
            }

            protected String doInBackground(Void... urls) {
                try {
                    String SheetID = getString(R.string.sheet_id);
                    String SheetName = getString(R.string.stats);
                    String link = "https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=" + SheetID + "&sheet=" + SheetName;


                    System.out.println(link);

                    URL url = new URL(link);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();

                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    System.out.println("ERROR : doInBackground");
                    //loadingDialog.dismiss();
                    Splashscreen.this.finish();
                    return null;
                }
            }

            protected void onPostExecute(final String response) {
                CallHolder.setStats(response);
            }
        }.execute();
    }

    public void getStandardHistory(){
        //Standard History
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
            }

            protected String doInBackground(Void... urls) {
                try {
                    String SheetID = getString(R.string.sheet_id);
                    String SheetName = getString(R.string.standard_history);
                    String link = "https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=" + SheetID + "&sheet=" + SheetName;


                    System.out.println(link);

                    URL url = new URL(link);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();

                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    System.out.println("ERROR : doInBackground");
                    //loadingDialog.dismiss();
                    Splashscreen.this.finish();
                    return null;
                }
            }

            protected void onPostExecute(final String response) {
                CallHolder.setStandard_old(response);
            }
        }.execute();
    }

    public void getAltHistory(){
        //Tameiarxis History
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
            }

            protected String doInBackground(Void... urls) {
                try {
                    String SheetID = getString(R.string.sheet_id);
                    String SheetName = getString(R.string.tameiarxis_history);
                    String link = "https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=" + SheetID + "&sheet=" + SheetName;


                    System.out.println(link);

                    URL url = new URL(link);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();

                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    System.out.println("ERROR : doInBackground");
                    //loadingDialog.dismiss();
                    Splashscreen.this.finish();
                    return null;
                }
            }

            protected void onPostExecute(final String response) {
                CallHolder.setTameiarxis_old(response);
            }
        }.execute();
    }

    public void getBonusHistory(){
        //Bonus History
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
            }

            protected String doInBackground(Void... urls) {
                try {
                    String SheetID = getString(R.string.sheet_id);
                    String SheetName = getString(R.string.andriko_history);
                    String link = "https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=" + SheetID + "&sheet=" + SheetName;


                    System.out.println(link);

                    URL url = new URL(link);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();

                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    System.out.println("ERROR : doInBackground");
                    //loadingDialog.dismiss();
                    Splashscreen.this.finish();
                    return null;
                }
            }

            protected void onPostExecute(final String response) {
                CallHolder.setBonus_old(response);
            }
        }.execute();
    }



}
