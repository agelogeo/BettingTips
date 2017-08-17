package gr.betting.admin.bettingtips;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.vending.billing.IInAppBillingService;

import gr.betting.admin.bettingtips.util.IabHelper;
import gr.betting.admin.bettingtips.util.IabResult;
import gr.betting.admin.bettingtips.util.Inventory;
import gr.betting.admin.bettingtips.util.Purchase;

/**
 * Created by Admin on 19/6/2017.
 */

public class SupportFragment extends Fragment {
    String license_key_one = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAilx49B8+wMZ6ZeTICJaTHZQQyMU+nVrO0mom3laf5/Jl9QTB9n5QcacG9wbeCuyJN392vUgaHk66Yy7mQgSB0qNl3pFAzlZfPGmC8BKNsVPm94JZ1Is2lS/";
    String license_key_two = "X4Wq6Hkz3K0leG5R30/m9eS8IeGUbi/BmtHSP67GaLXetKrkKiQOE4NwuEqI9ooxaspEMgcRuJ0R+w2YfXZQrCITFB4AbS68vqf24dayQFqfd37OrMzkEEihVpdQvfH7Yz";
    String license_key_three = "+IeTfKSCfu/KfYo93N2BOMmsyXS1MAg1rg/ZOA1A4x/dF3rQr03hZWm9VnnTt82KTgx61HoJzBdx7dZ9YasxwrA1zGSAQIDAQAB";
    private static final String TAG = "bettingtips";
    private Button friendlyButton,goldButton,vipButton,subButton;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
            View v = inflater.inflate(R.layout.support_layout, null);

            if(CallHolder.getIsSubscriber()){
                LinearLayout vip_member_layout = (LinearLayout) v.findViewById(R.id.vip_member_layout);
                vip_member_layout.setVisibility(View.GONE);
            }else{
                vipButton = (Button)v.findViewById(R.id.vipButton);

                vipButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle buyIntentBundle = null;
                        try {
                            buyIntentBundle = CallHolder.getmService().getBuyIntent(3,getActivity().getPackageName(),
                                    getString(R.string.vip), "inapp", "");
                            PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

                            getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(),
                                    1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
                                    Integer.valueOf(0));

                            //int response = CallHolder.getmService().consumePurchase(3, getActivity().getPackageName(), token);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            friendlyButton = (Button)v.findViewById(R.id.friendlyButton);
            friendlyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle buyIntentBundle = null;
                    try {
                        buyIntentBundle = CallHolder.getmService().getBuyIntent(3,getActivity().getPackageName(),
                                getString(R.string.friendly), "inapp", "");
                        PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

                        getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(),
                                1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
                                Integer.valueOf(0));


                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            });

            subButton = (Button)v.findViewById(R.id.subButton);
            subButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle buyIntentBundle = null;
                    try {
                        buyIntentBundle = CallHolder.getmService().getBuyIntent(3,getActivity().getPackageName(),
                                getString(R.string.subscriber), "subs", "");
                        PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

                        getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(),
                                1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
                                Integer.valueOf(0));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            });



            this.getActivity().setTitle(getString(R.string.nav_support));

            return v;
        }


}
