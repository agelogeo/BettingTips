package gr.betting.admin.bettingtips;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 22/6/2017.
 */

public class TodayFragment extends Fragment  {
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView adView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabs_layout,null);


        RelativeLayout adViewContainer = (RelativeLayout) v.findViewById(R.id.adViewContainer);

        adView = new AdView(getActivity(), getString(R.string.today_banner), AdSize.BANNER_320_50);
        adViewContainer.addView(adView);
        AdSettings.addTestDevice("1a423b3fe2e8ab23617f457578f1ff44");
        adView.loadAd();


        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        Fragment childFragment = new StandardNewTipsFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main2, childFragment).commit();

        TabLayout tabs = (TabLayout) v.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(getString(R.string.tab_standard)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.tab_alternative)));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Bundle bundle = new Bundle();
                bundle.putString("today_option",(String)tab.getText());
                mFirebaseAnalytics.logEvent("today_tabs",bundle);

                if(tab.getPosition()==0){
                    Fragment childFragment = new StandardNewTipsFragment();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
                    transaction.replace(R.id.content_main2, childFragment).commit();
                }else if (tab.getPosition()==1){
                    Fragment childFragment = new TameiarxisNewTipsFragment();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
                    transaction.replace(R.id.content_main2, childFragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return v;
    }

}
