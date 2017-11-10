package gr.betting.admin.bettingtips;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.facebook.ads.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, InterstitialAdListener,ServiceConnection {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private int id;
    private InterstitialAd interstitialAd;
    private ShareActionProvider shareActionProvider;
    NavigationView navigationView;
    private AdView adView;

    IInAppBillingService mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
        System.out.println("SERVICE STARTED");






        // Get the app's shared preferences
        CallHolder.setApp_preferences(PreferenceManager.getDefaultSharedPreferences(this));

        RelativeLayout adViewContainer = (RelativeLayout) findViewById(R.id.adViewContainer);

        if(CallHolder.getApp_preferences().getBoolean("show_ads",true)){
            if(CallHolder.getAdView()!=null){
                adViewContainer.addView(CallHolder.getAdView());
                System.out.println("ADVIEW : DONE");
            }else
                System.out.println("ADVIEW : NULL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }else{
            adViewContainer.setVisibility(View.GONE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AskRating();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Setting default fragment
        id=R.id.nav_news_tips;
        navigationView.setCheckedItem(R.id.nav_news_tips);
        Fragment  fragment = new TodayFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame,fragment);
        transaction.commit();


    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        mService = IInAppBillingService.Stub.asInterface(service);
        CallHolder.setmService(mService);

        try {
            Bundle ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);
            Bundle ownedSubs = mService.getPurchases(3, getPackageName(), "subs", null);

            int response = ownedItems.getInt("RESPONSE_CODE");
            int responseSubs = ownedItems.getInt("RESPONSE_CODE");
            if (response == 0 && responseSubs == 0) {
                ArrayList<String> ownedSkus =
                        ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");

                ArrayList<String> ownedSubsStringArrayList =
                        ownedSubs.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");

                System.out.println("OWNED !!!!!!! :"+ownedSkus);
                System.out.println("OWNED !!!!!!! :"+ownedSubsStringArrayList);

                if(ownedSkus.contains(getString(R.string.vip)) || ownedSubsStringArrayList.contains(getString(R.string.subscriber))){
                    CallHolder.setIsSubscriber(true);
                    RelativeLayout adViewContainer = (RelativeLayout) findViewById(R.id.adViewContainer);
                    adViewContainer.setVisibility(View.GONE);
                }else{
                    CallHolder.setIsSubscriber(false);
                }
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }


        ArrayList<String> skuList = new ArrayList<String> ();
        skuList.add(getString(R.string.friendly));
        skuList.add(getString(R.string.vip));
        skuList.add(getString(R.string.subscriber));
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

        try {
            Bundle skuDetails = mService.getSkuDetails(3,
                    getPackageName(), "inapp", querySkus);

            int response = skuDetails.getInt("RESPONSE_CODE");
            if (response == 0) {
                ArrayList<String> responseList
                        = skuDetails.getStringArrayList("DETAILS_LIST");

                for (String thisResponse : responseList) {
                    JSONObject object = new JSONObject(thisResponse);
                    String sku = object.getString("productId");
                    String price = object.getString("price");
                    System.out.println("----/ "+sku+" "+price+"/----");

                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Bundle skuDetails = mService.getSkuDetails(3,
                    getPackageName(), "subs", querySkus);

            int response = skuDetails.getInt("RESPONSE_CODE");
            if (response == 0) {
                ArrayList<String> responseList
                        = skuDetails.getStringArrayList("DETAILS_LIST");

                CallHolder.setSubsresponseList(responseList);

                for (String thisResponse : responseList) {
                    JSONObject object = new JSONObject(thisResponse);
                    String sku = object.getString("productId");
                    String price = object.getString("price");
                    System.out.println("----/ "+sku+" "+price+"/----");

                }


            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Bundle ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);
            int response = ownedItems.getInt("RESPONSE_CODE");
            if (response == 0) {
                ArrayList<String> ownedSkus =
                        ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> purchaseDataList =
                        ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> signatureList =
                        ownedItems.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                String continuationToken =
                        ownedItems.getString("INAPP_CONTINUATION_TOKEN");

                for (int i = 0; i < purchaseDataList.size(); ++i) {
                    String purchaseData = purchaseDataList.get(i);
                    String signature = signatureList.get(i);
                    String sku = ownedSkus.get(i);

                    System.out.println(" -> "+purchaseData+" "+signature+" "+sku);

                    // do something with this purchase information
                    // e.g. display the updated list of products owned by user
                }

                // if continuationToken != null, call getPurchases ag
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }



        System.out.println("SERVICE CONNECTED");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e("INAPP","SERVICE DISCONNECTED");
        mService = null;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (id == R.id.nav_news_tips) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                // ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.quit_dialog, null);
                dialogBuilder.setView(dialogView);
                //dialogBuilder.setTitle("Edit Coupon");
                final AlertDialog alertDialog = dialogBuilder.create();
                // set the custom dialog components - text, image and button

                Button noButton = (Button) dialogView.findViewById(R.id.dialog_no_btn);
                Button yesButton = (Button) dialogView.findViewById(R.id.dialog_yes_btn);

                // if button is clicked, close the custom dialog
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        MainActivity.super.onBackPressed();
                    }
                });

                alertDialog.show();
            }else{
                id=R.id.nav_news_tips;
                navigationView.setCheckedItem(R.id.nav_news_tips);
                Fragment fragment = new TodayFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.nav_enter,R.anim.nav_exit);
                transaction.replace(R.id.mainFrame,fragment);
                transaction.commit();

            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    if(sku.equals(getString(R.string.friendly))) {
                        Toast.makeText(this, "You have bought the " + sku + ". Excellent choice,adventurer!", Toast.LENGTH_LONG).show();

                        int response = mService.consumePurchase(3, getPackageName(), jo.getString("purchaseToken"));
                    }
                    else{
                        Toast.makeText(this, "You have bought the " + sku + ".", Toast.LENGTH_LONG).show();

                    }
                }
                catch (JSONException e) {
                    //alert("Failed to parse purchase data.");
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if(shareActionProvider != null){
            shareActionProvider.setShareIntent(createShareAppIntent());
            Log.e(LOG_TAG, "intent set to share action provider ");
        }
        else{
            Log.e(LOG_TAG, "Share action provider is null?");
        }
        //super.onCreateOptionsMenu(menu);


        // Return true to display menu
        return true;
    }


    private Intent createShareAppIntent(){
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message));
        sendIntent.setType("text/plain");
        return sendIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_news_tips) {
            fragment = new TodayFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.nav_enter,R.anim.nav_exit);
            transaction.replace(R.id.mainFrame,fragment);
            transaction.commit();
            if(!CallHolder.getIsSubscriber())
                loadInterstitialAd();
        } else if (id == R.id.nav_old_tips) {
            fragment = new HistoryFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.nav_enter,R.anim.nav_exit);
            transaction.replace(R.id.mainFrame,fragment);
            transaction.commit();
            if(!CallHolder.getIsSubscriber())
                loadInterstitialAd();
        } else if (id == R.id.nav_feedback) {
            final String appName = getApplicationContext().getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id="
                                + appName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id="
                                + appName)));
            }
        } else if (id == R.id.nav_bonus_tips) {
            fragment = new BonusFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.nav_enter,R.anim.nav_exit);
            transaction.replace(R.id.mainFrame,fragment);
            transaction.commit();
            if(!CallHolder.getIsSubscriber())
                loadInterstitialAd();
        }else if (id == R.id.nav_guides) {
            fragment = new GuideFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.nav_enter,R.anim.nav_exit);
            transaction.replace(R.id.mainFrame,fragment);
            transaction.commit();
        } else if (id == R.id.nav_donate) {
            final String paypallink = "https://www.paypal.me/agelogeo";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(paypallink)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(paypallink)));
            }
        } else if (id == R.id.nav_support) {
            navigationView.setCheckedItem(R.id.nav_support);
            fragment = new SupportFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.nav_enter,R.anim.nav_exit);
            transaction.replace(R.id.mainFrame,fragment);
            transaction.commit();
        }else if (id == R.id.nav_send) {
            if(shareActionProvider != null){
                startActivity(createShareAppIntent());
                Log.e(LOG_TAG, "intent set to share action provider ");
            }
            else{
                Log.e(LOG_TAG, "Share action provider is null?");
            }
        }   else if (id == R.id.nav_info) {
            navigationView.setCheckedItem(R.id.nav_info);
            fragment = new InfoFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.nav_enter,R.anim.nav_exit);
            transaction.replace(R.id.mainFrame,fragment);
            transaction.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            trimCache(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mService != null) {
            unbindService(this);
        }
    }


    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    public void AskRating(){
        // Count times app has been opened, display rating message after number of times
        // By Rafael Duval
        try {



            // Get the app's shared preferences
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

            // Get the value for the run counter
            int counter = app_preferences.getInt("counter_starts", 0);

            // Do every x times
            //int RunEvery = 20;

            if(counter != 0  && counter == 5 || counter == 20 || counter == 50 || counter == 150 || counter == 300 )

            {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle(R.string.we_need_your_help);
                alert.setIcon(R.drawable.ic_loyalty_black_24dp); //app icon here
                alert.setMessage(R.string.please_take_a_moment);

                alert.setPositiveButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                //Do nothing
                            }
                        });

                alert.setNegativeButton(R.string.rate_it,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                final String appName = getApplicationContext().getPackageName();
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id="
                                                    + appName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("http://play.google.com/store/apps/details?id="
                                                    + appName)));
                                }

                            }
                        });
                alert.show();
            }
            Log.d("RATE ","This app has been started " + counter + " times.");
            //Toast.makeText(this, "This app has been started " + counter + " times.", Toast.LENGTH_SHORT).show();


            // Increment the counter
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putInt("counter_starts", ++counter);
            editor.commit(); // Very important

        } catch (Exception e) {
            //Do nothing, don't run but don't break
        }
    }

    private void loadInterstitialAd() {
        interstitialAd = new InterstitialAd(this, getString(R.string.interstitial_ad));
        interstitialAd.setAdListener(this);
        interstitialAd.loadAd();
    }

    @Override
    public void onError(Ad ad, AdError error) {
        // Ad failed to load
    }

    @Override
    public void onAdLoaded(Ad ad) {
        // Ad is loaded and ready to be displayed
        // You can now display the full screen add using this code:
        interstitialAd.show();
    }

    @Override
    public void onAdClicked(Ad ad) {

    }

    @Override
    public void onLoggingImpression(Ad ad) {

    }

    @Override
    public void onInterstitialDisplayed(Ad ad) {

    }

    @Override
    public void onInterstitialDismissed(Ad ad) {
        interstitialAd.destroy();

    }




    /**
     * This function assumes logger is an instance of AppEventsLogger and has been
     * created using AppEventsLogger.newLogger() call.
     */

}
