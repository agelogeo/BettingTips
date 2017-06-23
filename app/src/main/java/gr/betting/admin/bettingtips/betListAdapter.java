package gr.betting.admin.bettingtips;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 14/6/2017.
 */

public class betListAdapter extends ArrayAdapter<betItem> {

    public betListAdapter(Context context, ArrayList<betItem> Listrowdata) {
        super(context, 0, Listrowdata);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        betItem betitem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bet_layout, parent, false);
        }

        // Lookup view for data population
        //Header layout
        LinearLayout header = (LinearLayout) convertView.findViewById(R.id.header_layout);

        TextView country_league = (TextView) header.findViewById(R.id.country_league);
        TextView time = (TextView) header.findViewById(R.id.time);
        TextView date = (TextView) header.findViewById(R.id.date);
        String[] date_temp = betitem.getDate().split("-");
        date.setText(date_temp[2]+"/"+date_temp[1]+"/"+date_temp[0]);
        country_league.setText(betitem.getCountry_league());
        time.setText(betitem.getTime());

        //Details layout
        LinearLayout details = (LinearLayout) convertView.findViewById(R.id.details_layout);


        //Teams layout
        TextView home_team_name = (TextView) details.findViewById(R.id.home_team_name);
        TextView away_team_name = (TextView) details.findViewById(R.id.away_team_name);
        home_team_name.setText(betitem.getHome_team_name());
        away_team_name.setText(betitem.getAway_team_name());

        //Score layout
        TextView home_team_score = (TextView) details.findViewById(R.id.home_team_score);
        TextView away_team_score = (TextView) details.findViewById(R.id.away_team_score);
        home_team_score.setText(betitem.getHome_team_score());
        away_team_score.setText(betitem.getAway_team_score());

        //Odd
        TextView odd = (TextView) details.findViewById(R.id.odd);
        odd.setText(betitem.getOdd());

        //Tip
        TextView tip = (TextView) convertView.findViewById(R.id.tip);
        tip.setText(betitem.getTip());

        if(betitem.getGotcha().toUpperCase().equals("TRUE")){
            odd.setBackground(getContext().getResources().getDrawable(R.drawable.bet_odd_shape_positive));
        }else if(betitem.getGotcha().toUpperCase().equals("FALSE")){
            odd.setBackground(getContext().getResources().getDrawable(R.drawable.bet_odd_shape_negative));
        }else{
            odd.setBackground(getContext().getResources().getDrawable(R.drawable.bet_odd_shape));
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
