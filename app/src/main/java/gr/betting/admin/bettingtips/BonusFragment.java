package gr.betting.admin.bettingtips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class BonusFragment extends Fragment implements MoPubInterstitial.InterstitialAdListener, MoPubView.BannerAdListener {
    private InterstitialAd interstitial;

    MoPubView moPubView;
    MoPubInterstitial mInterstitial;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabs_layout,null);

        mInterstitial = new MoPubInterstitial(getActivity(), getString(R.string.mp_full_ad));
        mInterstitial.setInterstitialAdListener(this);
        mInterstitial.load();

        moPubView = (MoPubView) v.findViewById(R.id.adview);
        moPubView.setAdUnitId(getString(R.string.mp_bonus_today));
        moPubView.setAutorefreshEnabled(true);
        moPubView.loadAd();
        moPubView.setBannerAdListener(this);

        /*AdRequest adRequest = new AdRequest.Builder().build();

        //Prepare the Interstitial Ad
        interstitial = new InterstitialAd(getActivity());
        //Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.full_screen_ad_unit_id));

        interstitial.loadAd(adRequest);

        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLoaded() {
                displayInterstitial();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }
        });*/


        Fragment childFragment = new AndrikoNewTipsFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main2, childFragment).commit();

        TabLayout tabs = (TabLayout) v.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(getString(R.string.bonus_today)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.bonus_history)));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    Fragment childFragment = new AndrikoNewTipsFragment();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
                    transaction.replace(R.id.content_main2, childFragment).commit();
                }else if (tab.getPosition()==1){
                    Fragment childFragment = new AndrikoOldTipsFragment();
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

    public void displayInterstitial(){
        if(interstitial.isLoaded()){
            interstitial.show();
        }
    }

    @Override
    public void onInterstitialLoaded(MoPubInterstitial interstitial) {
        if (interstitial.isReady()) {
            mInterstitial.show();
        } else {
            // Other code
        }
    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {

    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial) {

    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial) {

    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial) {

    }

    @Override
    public void onDestroyView() {
        mInterstitial.destroy();
        moPubView.destroy();

        super.onDestroyView();
    }

    @Override
    public void onBannerLoaded(MoPubView banner) {

    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {

    }

    @Override
    public void onBannerClicked(MoPubView banner) {

    }

    @Override
    public void onBannerExpanded(MoPubView banner) {

    }

    @Override
    public void onBannerCollapsed(MoPubView banner) {

    }
}
