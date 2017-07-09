package gr.betting.admin.bettingtips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Admin on 9/7/2017.
 */

public class StatsFragment extends Fragment {
    private FirebaseAnalytics mFirebaseAnalytics;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.stats_layout,null);


        this.getActivity().setTitle(getString(R.string.stats_title));

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString("stats","stats");
        mFirebaseAnalytics.logEvent("stats",bundle);

        String response = CallHolder.getStats();
        try {
            if(response==null)
                Toast.makeText(StatsFragment.this.getActivity(), "No available stats", Toast.LENGTH_LONG).show();
            else{
                if (!response.contains("The coordinates or dimensions of the range are invalid.")) {
                    JSONObject jsonResult = new JSONObject(response);
                    JSONArray results = (JSONArray) jsonResult.get(getString(R.string.stats));

                    for(int i=0;i<results.length();i++){
                        JSONObject source = results.getJSONObject(i);
                        TextView total=null,won=null,lost=null,perc=null;
                        double p;
                        String ps;
                        DecimalFormat df = new DecimalFormat("#.##");
                        switch(i){
                            case 0:
                                total = (TextView) v.findViewById(R.id.standard_tips_total_num);
                                won = (TextView) v.findViewById(R.id.standard_tips_won_num);
                                lost = (TextView) v.findViewById(R.id.standard_tips_lost_num);
                                perc = (TextView) v.findViewById(R.id.standard_tips_perc_num);
                                break;
                            case 1:
                                total = (TextView) v.findViewById(R.id.alt_tips_total_num);
                                won = (TextView) v.findViewById(R.id.alt_tips_won_num);
                                lost = (TextView) v.findViewById(R.id.alt_tips_lost_num);
                                perc = (TextView) v.findViewById(R.id.alt_tips_perc_num);
                                break;
                            case 2:
                                total = (TextView) v.findViewById(R.id.bonus_tips_total_num);
                                won = (TextView) v.findViewById(R.id.bonus_tips_won_num);
                                lost = (TextView) v.findViewById(R.id.bonus_tips_lost_num);
                                perc = (TextView) v.findViewById(R.id.bonus_tips_perc_num);
                                break;
                        }
                        p = Double.parseDouble(source.getString("SUCCESS"));
                        if(df.format(p).length()<4)
                            ps = df.format(p)+"0%";
                        else
                            ps = df.format(p)+"%";

                        total.setText(source.getString("TOTAL"));
                        won.setText(source.getString("WON"));
                        lost.setText(source.getString("LOST"));
                        perc.setText(ps.substring(2));

                    }






                } else {
                    System.out.println("No available stats");


                }
            }

        } /*catch (JSONException e) {
            System.out.println("ERROR : onPostExecute");
            StatsFragment.this.getActivity().finish();
            e.printStackTrace();
        } */catch (Exception e){
            System.out.println("ERROR : onPostExecute");
            e.printStackTrace();
        }

        return v;
    }


}
