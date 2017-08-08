package gr.betting.admin.bettingtips;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Admin on 19/6/2017.
 */

public class SupportFragment extends Fragment {

    String license_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAilx49B8+wMZ6ZeTICJaTHZQQyMU+nVrO0mom3laf5/Jl9QTB9n5QcacG9wbeCuyJN392vUgaHk66Yy7mQgSB0qNl3pFAzlZfPGmC8BKNsVPm94JZ1Is2lS/X4Wq6Hkz3K0leG5R30/m9eS8IeGUbi/BmtHSP67GaLXetKrkKiQOE4NwuEqI9ooxaspEMgcRuJ0R+w2YfXZQrCITFB4AbS68vqf24dayQFqfd37OrMzkEEihVpdQvfH7Yz+IeTfKSCfu/KfYo93N2BOMmsyXS1MAg1rg/ZOA1A4x/dF3rQr03hZWm9VnnTt82KTgx61HoJzBdx7dZ9YasxwrA1zGSAQIDAQAB";


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
            View v = inflater.inflate(R.layout.support_layout, null);



            /*// Increment the counter
            SharedPreferences.Editor editor = CallHolder.getApp_preferences().edit();
            editor.putBoolean("show_ads",false);
            editor.apply(); // Very important*/

            this.getActivity().setTitle(getString(R.string.nav_support));
            TextView version = (TextView) v.findViewById(R.id.version);
            version.setText(" "+BuildConfig.VERSION_NAME);

            return v;
        }
}
