package gr.betting.admin.bettingtips;

import android.content.Intent;
import android.os.Bundle;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MobileAds.initialize(this, "ca-app-pub-1785790420897011~3331999883");





        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Setting default fragment
        navigationView.setCheckedItem(R.id.nav_news_tips);
        Fragment  fragment = new StandardNewTipsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame,fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.     https://www.youtube.com/watch?v=HZ3UWScq_0Y");
        sendIntent.setType("text/plain");
        return sendIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_share) {

            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_news_tips) {
            fragment = new TodayFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFrame,fragment);
            //transaction.disallowAddToBackStack();
            //transaction.addToBackStack();
            transaction.commit();
        } else if (id == R.id.nav_old_tips) {
            fragment = new HistoryFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFrame,fragment);
            //transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_feedback) {
            Toast.makeText(this,"This function is not implemented yet.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_bonus_tips) {
            fragment = new BonusFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFrame,fragment);
            //transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_send) {
            if(shareActionProvider != null){
                startActivity(createShareAppIntent());
                Log.e(LOG_TAG, "intent set to share action provider ");
            }
            else{
                Log.e(LOG_TAG, "Share action provider is null?");
            }
        }   else if (id == R.id.nav_info) {
            fragment = new InfoFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFrame,fragment);
            //transaction.addToBackStack(null);
            transaction.commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
