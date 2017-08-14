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
    private Button clickButton;
    private Button testButton,friendlyButton,goldButton,vipButton,subButton;


    IabHelper mHelper;
    static final String ITEM_SKU = "com.example.vip_donation";
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
            View v = inflater.inflate(R.layout.support_layout, null);

            testButton = (Button)v.findViewById(R.id.testButton);
            friendlyButton = (Button)v.findViewById(R.id.friendlyButton);
            goldButton = (Button)v.findViewById(R.id.goldButton);
            vipButton = (Button)v.findViewById(R.id.vipButton);
            subButton = (Button)v.findViewById(R.id.subButton);

            friendlyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle buyIntentBundle = null;
                    try {
                        buyIntentBundle = CallHolder.getmService().getBuyIntent(3,getActivity().getPackageName(),
                                "friendly_donation", "inapp", "");
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

            goldButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle buyIntentBundle = null;
                    try {
                        buyIntentBundle = CallHolder.getmService().getBuyIntent(3,getActivity().getPackageName(),
                                "gold_donation", "inapp", "");
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

            vipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle buyIntentBundle = null;
                    try {
                        buyIntentBundle = CallHolder.getmService().getBuyIntent(3,getActivity().getPackageName(),
                                "vip_donation", "inapp", "");
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

            subButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle buyIntentBundle = null;
                    try {
                        buyIntentBundle = CallHolder.getmService().getBuyIntent(3,getActivity().getPackageName(),
                                "subscriber", "subs", "");
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





            /*testButton.setOnClickListener(new View.OnClickListener() {
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



            mHelper = new IabHelper(getContext(), license_key_one+license_key_two+license_key_three);

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
*/

            this.getActivity().setTitle(getString(R.string.nav_support));

            return v;
            /*
            // Increment the counter
            SharedPreferences.Editor editor = CallHolder.getApp_preferences().edit();
            editor.putBoolean("show_ads",false);
            editor.apply(); // Very important*/
        }




        /*
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
            else if (purchase.getSku().equals(ITEM_SKU)) {
                consumeItem();
                testButton.setEnabled(false);
            }

        }
    };

    public void consumeItem() {
        try {
            mHelper.queryInventoryAsync(mReceivedInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
                try {
                    mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                            mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        clickButton.setEnabled(true);
                    } else {
                        // handle error
                    }
                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) try {
            mHelper.dispose();
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
        mHelper = null;
    }
    */
}
