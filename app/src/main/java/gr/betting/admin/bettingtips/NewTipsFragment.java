package gr.betting.admin.bettingtips;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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

public class NewTipsFragment extends Fragment {
    @Nullable
    @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstance){
        View v = inflater.inflate(R.layout.new_tips_layout,null);

        this.getActivity().setTitle("Today's Tips");

        final ProgressDialog loadingDialog = new ProgressDialog(this.getActivity());
        loadingDialog.setTitle("Please wait..");
        loadingDialog.setMessage("Loading tips...");
        loadingDialog.setCancelable(false);
        loadingDialog.show();


        final ListView listView = (ListView) v.findViewById(R.id.listview);


        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
            }

            protected String doInBackground(Void... urls) {
                try {
                    String SheetID = "175XqnHhcOS8CVoBY08xMhaDO39VmauBEFKU9qeaHS3U";
                    String SheetName = "2017";
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
                    NewTipsFragment.this.getActivity().finish();
                    return null;
                }
            }
            protected void onPostExecute(final String response) {
                final ArrayList<betItem> adapterList = new ArrayList<betItem>();
                try {
                    if(response==null)
                        Toast.makeText(NewTipsFragment.this.getActivity(), "No available tips", Toast.LENGTH_LONG).show();
                    else{
                        JSONObject jsonResult = new JSONObject(response);
                        final JSONArray results = (JSONArray) jsonResult.get("2017");
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


                        betListAdapter myAdapter = new betListAdapter(NewTipsFragment.this.getActivity(), adapterList );
                        listView.setAdapter(myAdapter);

                        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent DetailIntent = new Intent(SearchFlights.this, ListViewItemDetail.class);
                                JSONObject jsonResult = null;
                                try {
                                    jsonResult = new JSONObject(response);
                                    JSONArray results = (JSONArray) jsonResult.get("results");
                                    JSONObject result = results.getJSONObject(adapterList.get(position).getResult_indicator());
                                    DetailIntent.putExtra("JSON_result", result.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                DetailIntent.putExtra("LVI_itinerary_ind",adapterList.get(position).getItinerary_indicator());
                                startActivity(DetailIntent);
                            }
                        });*/
                    }

                } catch (JSONException e) {
                    System.out.println("ERROR : onPostExecute");
                    loadingDialog.dismiss();
                    NewTipsFragment.this.getActivity().finish();
                    e.printStackTrace();
                } catch (Exception e){
                    loadingDialog.dismiss();
                    System.out.println("ERROR : onPostExecute");
                    NewTipsFragment.this.getActivity().finish();
                    e.printStackTrace();
                }
            }
        }.execute();

        return v;
    }


}
