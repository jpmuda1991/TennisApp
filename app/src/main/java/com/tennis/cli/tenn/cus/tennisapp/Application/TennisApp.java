package com.tennis.cli.tenn.cus.tennisapp.Application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.tennis.cli.tenn.cus.tennisapp.Models.Competitor;
import com.tennis.cli.tenn.cus.tennisapp.Models.CompetitorsArr;
import com.tennis.cli.tenn.cus.tennisapp.Models.PlayersModel;

import java.util.ArrayList;
import java.util.List;


public class TennisApp extends Application {

    private static final String TAG = "AlphaMoversApp";
    public static String CHANNEL_ID = "UPLOAD";
    public static String CHANNEL_ERROR = "ERROR_ID";

    public static List<CompetitorsArr> maleRankingsList  = new ArrayList<>();
    public static List<CompetitorsArr> femaleRankingsList = new ArrayList<>();

    public static PlayersModel selectedPlayerModel;
    public static String from = "";
    public static String samelMaleOrFemale = "";
    public static String sameCountry = "";


    public static PlayersModel getSelectedPlayerModel() {
        return selectedPlayerModel;
    }

    public static void setSelectedPlayerModel(PlayersModel selectedPlayerModel) {
        TennisApp.selectedPlayerModel = selectedPlayerModel;
    }

    public static String getFrom() {
        return from;
    }

    public static void setFrom(String from) {
        TennisApp.from = from;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    public static String getSameCountry() {
        return sameCountry;
    }

    public static void setSameCountry(String sameCountry) {
        TennisApp.sameCountry = sameCountry;
    }

    public static String getSamelMaleOrFemale() {
        return samelMaleOrFemale;
    }

    public static void setSamelMaleOrFemale(String samelMaleOrFemale) {
        TennisApp.samelMaleOrFemale = samelMaleOrFemale;
    }

    public static List<CompetitorsArr> getMaleRankingsList() {
        return maleRankingsList;
    }

    public static void setMaleRankingsList(List<CompetitorsArr> maleRankingsList) {

        if (TennisApp.maleRankingsList != null){
            TennisApp.maleRankingsList.clear();
            TennisApp.maleRankingsList.addAll(maleRankingsList);
        }
    }

    public static List<CompetitorsArr> getFemaleRankingsList() {
        return femaleRankingsList;
    }

    public static void setFemaleRankingsList(List<CompetitorsArr> femaleRankingsList) {
        if (TennisApp.femaleRankingsList != null){
            TennisApp.femaleRankingsList.clear();
            TennisApp.femaleRankingsList.addAll(femaleRankingsList);
        }
    }

    @Override public void onCreate() {
        super.onCreate();

//        createNotificationChannel();
    }


    //     the NotificationChannel class is new and not in the support library
//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            if (notificationManager != null) {
//                notificationManager.createNotificationChannel(channel);
//            }
//        }
//    }

}

