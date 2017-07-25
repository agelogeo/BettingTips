package gr.betting.admin.bettingtips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.ads.*;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;

/**
 * Created by Admin on 22/6/2017.
 */

public class GuideFragment extends Fragment  {
    private com.facebook.ads.AdView adView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabs_layout,null);

        Fragment childFragment = new GuideStrategiesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main2, childFragment).commit();

        TabLayout tabs = (TabLayout) v.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Strategies"));
        tabs.addTab(tabs.newTab().setText("Management"));
        tabs.addTab(tabs.newTab().setText("Streak wins"));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    Fragment childFragment = new GuideStrategiesFragment();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
                    transaction.replace(R.id.content_main2, childFragment).commit();
                }else if (tab.getPosition()==1){
                    Fragment childFragment = new GuideManagementFragment();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
                    transaction.replace(R.id.content_main2, childFragment).commit();
                }else if (tab.getPosition()==2){
                    Fragment childFragment = new GuideRowFragment();
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
