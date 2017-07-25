package gr.betting.admin.bettingtips;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Admin on 19/6/2017.
 */

public class GuideRowFragment extends Fragment  {
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
