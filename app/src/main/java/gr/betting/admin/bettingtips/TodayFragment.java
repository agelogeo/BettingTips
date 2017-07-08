package gr.betting.admin.bettingtips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

/**
 * Created by Admin on 22/6/2017.
 */

public class TodayFragment extends Fragment implements MoPubView.BannerAdListener {
    private FirebaseAnalytics mFirebaseAnalytics;

    MoPubView moPubView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabs_layout,null);

        moPubView = (MoPubView) v.findViewById(R.id.adview);
        moPubView.setAdUnitId(getString(R.string.mp_standard_today));
        moPubView.setAutorefreshEnabled(true);
        moPubView.setBannerAdListener(this);
        moPubView.loadAd();

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

    @Override
    public void onDestroyView() {
        moPubView.destroy();
        super.onDestroyView();
    }
}
