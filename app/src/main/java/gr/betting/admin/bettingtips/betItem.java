package gr.betting.admin.bettingtips;
/**
 * Created by Admin on 14/6/2017.
 */

public class betItem {
    //header layout
    String country_league,time,date;
    //details_layout
    String home_team_name,away_team_name;
    //score layout
    String home_team_score,away_team_score;

    String odd,tip;

    String gotcha;

    public betItem(String country_league, String time, String home_team_name, String away_team_name, String home_team_score, String away_team_score, String odd, String tip,String gotcha,String date) {
        this.country_league = country_league;
        this.time = time;
        this.home_team_name = home_team_name;
        this.away_team_name = away_team_name;
        this.home_team_score = home_team_score;
        this.away_team_score = away_team_score;
        this.odd = odd;
        this.tip = tip;
        this.gotcha = gotcha;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGotcha() {
        return gotcha;
    }

    public void setGotcha(String gotcha) {
        this.gotcha = gotcha;
    }

    public betItem() {
    }

    public String getCountry_league() {
        return country_league;
    }

    public void setCountry_league(String country_league) {
        this.country_league = country_league;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHome_team_name() {
        return home_team_name;
    }

    public void setHome_team_name(String home_team_name) {
        this.home_team_name = home_team_name;
    }

    public String getAway_team_name() {
        return away_team_name;
    }

    public void setAway_team_name(String away_team_name) {
        this.away_team_name = away_team_name;
    }

    public String getHome_team_score() {
        return home_team_score;
    }

    public void setHome_team_score(String home_team_score) {
        this.home_team_score = home_team_score;
    }

    public String getAway_team_score() {
        return away_team_score;
    }

    public void setAway_team_score(String away_team_score) {
        this.away_team_score = away_team_score;
    }

    public String getOdd() {
        return odd;
    }

    public void setOdd(String odd) {
        this.odd = odd;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
