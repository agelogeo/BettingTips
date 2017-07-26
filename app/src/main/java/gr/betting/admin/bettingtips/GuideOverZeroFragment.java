package gr.betting.admin.bettingtips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Admin on 19/6/2017.
 */

public class GuideOverZeroFragment extends Fragment  {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.guide_layout, null);

        WebView view = (WebView) v.findViewById(R.id.my_web);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl("file:///android_asset/over0.html");


        return v;
    }
}
