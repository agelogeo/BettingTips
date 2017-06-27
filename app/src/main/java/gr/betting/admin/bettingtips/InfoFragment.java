package gr.betting.admin.bettingtips;

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

public class InfoFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
            View v = inflater.inflate(R.layout.info_layout, null);

            this.getActivity().setTitle(getString(R.string.nav_about));
            TextView version = (TextView) v.findViewById(R.id.version);
            version.setText(" "+BuildConfig.VERSION_NAME);

            return v;
        }
    }
