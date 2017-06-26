package gr.betting.admin.bettingtips;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Admin on 19/6/2017.
 */

public class TameiarxisNewTipsFragment extends Fragment {
    private AdView mAdView;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Nullable
    @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstance){
        final View v = inflater.inflate(R.layout.tameiarxis_new_tips_layout,null);
// Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        Bundle bundle = new Bundle();
        bundle.putString("Tameiarxis_new_tips",(String)getActivity().getTitle());
        mFirebaseAnalytics.logEvent("Tameiarxis_new",bundle);

        mAdView = (AdView) v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        this.getActivity().setTitle(getString(R.string.nav_today));

        final ProgressDialog loadingDialog = new ProgressDialog(this.getActivity());
        loadingDialog.setTitle(getString(R.string.please_wait));
        loadingDialog.setMessage(getString(R.string.loading_tips));
        loadingDialog.setCancelable(false);
        loadingDialog.show();


        final ListView listView = (ListView) v.findViewById(R.id.listview);


        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
            }

            protected String doInBackground(Void... urls) {
                try {
                    String SheetID = getString(R.string.sheet_id);
                    String SheetName = getString(R.string.tameiarxis_today);
                    String link = "https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id="+SheetID+"&sheet="+SheetName;



                    System.out.println(link);

                    URL url = new URL(link);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();

                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    System.out.println("ERROR : doInBackground");
                    loadingDialog.dismiss();
                    TameiarxisNewTipsFragment.this.getActivity().finish();
                    return null;
                }
            }
            protected void onPostExecute(final String response) {
                final ArrayList<betItem> adapterList = new ArrayList<betItem>();
                try {
                    if(response==null)
                        Toast.makeText(TameiarxisNewTipsFragment.this.getActivity(), "No available tips", Toast.LENGTH_LONG).show();
                    else{
                        if (!response.contains("The coordinates or dimensions of the range are invalid.")) {
                            JSONObject jsonResult = new JSONObject(response);
                            final JSONArray results = (JSONArray) jsonResult.get(getString(R.string.tameiarxis_today));
                            int counter = 0;
                            for(int i=0;i<results.length();i++){
                                betItem tempItem = new betItem();
                                counter++;

                                JSONObject tip = results.getJSONObject(i);

                                tempItem.setDate(tip.getString("DATE"));
                                tempItem.setTime(tip.getString("TIME"));

                                tempItem.setHome_team_name(tip.getString("HOME_TEAM"));
                                tempItem.setAway_team_name(tip.getString("AWAY_TEAM"));

                                tempItem.setHome_team_score(tip.getString("HOME_SCORE"));
                                tempItem.setAway_team_score(tip.getString("AWAY_SCORE"));

                                tempItem.setOdd(tip.getString("ODD"));
                                tempItem.setTip(tip.getString("TIP"));

                                tempItem.setCountry_league(tip.getString("COUNTRY_LEAGUE"));
                                tempItem.setGotcha(tip.getString("Gotcha"));

                                adapterList.add(tempItem);

                            }
                            System.out.println("Counter : "+counter);
                            loadingDialog.dismiss();


                            betListAdapter myAdapter = new betListAdapter(TameiarxisNewTipsFragment.this.getActivity(), adapterList );
                            listView.setAdapter(myAdapter);

                        } else {
                            System.out.println("No available tips");
                            ImageView sad_face = (ImageView) v.findViewById(R.id.sad_face);
                            sad_face.setVisibility(View.VISIBLE);
                            TextView sorry_text = (TextView) v.findViewById(R.id.sorry_text);
                            sorry_text.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                            loadingDialog.dismiss();

                        }
                    }

                } catch (JSONException e) {
                    System.out.println("ERROR : onPostExecute");
                    loadingDialog.dismiss();
                    TameiarxisNewTipsFragment.this.getActivity().finish();
                    e.printStackTrace();
                } catch (Exception e){
                    loadingDialog.dismiss();
                    System.out.println("ERROR : onPostExecute");
                    e.printStackTrace();
                }
            }
        }.execute();

        return v;
    }


}
