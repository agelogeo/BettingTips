package gr.betting.admin.bettingtips;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import gr.betting.admin.bettingtips.util.IabHelper;
import gr.betting.admin.bettingtips.util.IabResult;
import gr.betting.admin.bettingtips.util.Purchase;

/**
 * Created by Admin on 19/6/2017.
 */

public class SupportFragment extends Fragment{
    String license_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAilx49B8+wMZ6ZeTICJaTHZQQyMU+nVrO0mom3laf5/Jl9QTB9n5QcacG9wbeCuyJN392vUgaHk66Yy7mQgSB0qNl3pFAzlZfPGmC8BKNsVPm94JZ1Is2lS/X4Wq6Hkz3K0leG5R30/m9eS8IeGUbi/BmtHSP67GaLXetKrkKiQOE4NwuEqI9ooxaspEMgcRuJ0R+w2YfXZQrCITFB4AbS68vqf24dayQFqfd37OrMzkEEihVpdQvfH7Yz+IeTfKSCfu/KfYo93N2BOMmsyXS1MAg1rg/ZOA1A4x/dF3rQr03hZWm9VnnTt82KTgx61HoJzBdx7dZ9YasxwrA1zGSAQIDAQAB";

    private static final String TAG = "bettingtips";
    private Button clickButton;
    private Button buyButton;

    IabHelper mHelper;
    static final String ITEM_SKU = "android.test.purchased";
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
            View v = inflater.inflate(R.layout.support_layout, null);

            buyButton = (Button)v.findViewById(R.id.buyButton);
            clickButton = (Button)v.findViewById(R.id.clickButton);
            clickButton.setEnabled(false);

            clickButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickButton.setEnabled(false);
                    buyButton.setEnabled(true);
                }
            });

            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mHelper.launchPurchaseFlow(getActivity(), ITEM_SKU, 10001,
                                mPurchaseFinishedListener, "mypurchasetoken");
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        e.printStackTrace();
                    }
                }
            });

            String base64EncodedPublicKey = license_key;

            mHelper = new IabHelper(getContext(), base64EncodedPublicKey);

            mHelper.startSetup(new
                                       IabHelper.OnIabSetupFinishedListener() {
                                           public void onIabSetupFinished(IabResult result)
                                           {
                                               if (!result.isSuccess()) {
                                                   Log.e(TAG, "In-app Billing setup failed: " +
                                                           result);
                                               } else {
                                                   Log.e(TAG, "In-app Billing is set up OK");
                                               }
                                           }
                                       });


            this.getActivity().setTitle(getString(R.string.nav_support));

            return v;
            /*
            // Increment the counter
            SharedPreferences.Editor editor = CallHolder.getApp_preferences().edit();
            editor.putBoolean("show_ads",false);
            editor.apply(); // Very important*/
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase)
        {
            if (result.isFailure()) {
                // Handle error
                return;
            }
            /*else if (purchase.getSku().equals(ITEM_SKU)) {
                consumeItem();
                buyButton.setEnabled(false);
            }*/

        }
    };
}
