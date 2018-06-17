package gr.betting.admin.bettingtips;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mopub.mobileads.MoPubView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Admin on 19/6/2017.
 */

public class StandardOldTipsFragment extends Fragment{
    private MoPubView moPubView;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstance){
        final View v = inflater.inflate(R.layout.standard_old_tips_layout,null);
/*
        moPubView = (MoPubView) v.findViewById(R.id.adview);
        moPubView.setAdUnitId(getString(R.string.mp_standard_history));
        moPubView.loadAd();
        moPubView.setBannerAdListener(this);*/

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        Bundle bundle = new Bundle();
        bundle.putString("Standard_old_tips",(String)getActivity().getTitle());
        mFirebaseAnalytics.logEvent("Standard_old",bundle);
        /*mAdView = (AdView) v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        this.getActivity().setTitle(getString(R.string.nav_history));



        final ListView listView = (ListView) v.findViewById(R.id.listview);

        String response = CallHolder.getStandard_old();
            final ArrayList<betItem> adapterList = new ArrayList<betItem>();
            try {
                if(response==null)
                    Toast.makeText(StandardOldTipsFragment.this.getActivity(), "No available tips", Toast.LENGTH_LONG).show();
                else{
                    if(!response.contains("The coordinates or dimensions of the range are invalid.")) {
                        System.out.println("No available tips ----------------" + response);
                        JSONArray results = new JSONArray(response);
                        //final JSONArray results = (JSONArray) jsonResult.get(getString(R.string.standard_today));
                        int counter = 0;
                        for(int i=1;i<results.length();i++){
                            betItem tempItem = new betItem();
                            counter++;

                            JSONArray tip = results.getJSONArray(i);

                            tempItem.setDate(tip.get(0).toString());
                            tempItem.setTime(tip.get(2).toString());

                            tempItem.setHome_team_name(tip.get(3).toString());
                            tempItem.setAway_team_name(tip.get(4).toString());

                            tempItem.setHome_team_score(tip.get(5).toString());
                            tempItem.setAway_team_score(tip.get(6).toString());

                            tempItem.setOdd(tip.get(7).toString());
                            tempItem.setTip(tip.get(8).toString());

                            tempItem.setCountry_league(tip.get(1).toString());
                            tempItem.setGotcha(tip.get(9).toString());

                            adapterList.add(tempItem);

                        }
                        System.out.println("Counter : " + counter);


                        betListAdapter myAdapter = new betListAdapter(StandardOldTipsFragment.this.getActivity(), adapterList);
                        listView.setAdapter(myAdapter);
                    }else{
                        System.out.println("No available tips");
                        ImageView sad_face = (ImageView) v.findViewById(R.id.sad_face);
                        sad_face.setVisibility(View.VISIBLE);
                        TextView sorry_text = (TextView) v.findViewById(R.id.sorry_text);
                        sorry_text.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);

                    }

                }

            } catch (JSONException e) {
                System.out.println(response);
                System.out.println("ERROR : onPostExecute");
                StandardOldTipsFragment.this.getActivity().finish();
                e.printStackTrace();
            } catch (Exception e){
                System.out.println("ERROR : onPostExecute");
                StandardOldTipsFragment.this.getActivity().finish();
                e.printStackTrace();
            }





        return v;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
