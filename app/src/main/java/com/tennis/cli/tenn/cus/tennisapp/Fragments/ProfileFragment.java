package com.tennis.cli.tenn.cus.tennisapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tennis.cli.tenn.cus.tennisapp.Activities.MainActivity;
import com.tennis.cli.tenn.cus.tennisapp.Application.TennisApp;
import com.tennis.cli.tenn.cus.tennisapp.Application.TennisCommon;
import com.tennis.cli.tenn.cus.tennisapp.Models.PlayersModel;
import com.tennis.cli.tenn.cus.tennisapp.Models.Profile.CprofileModel;
import com.tennis.cli.tenn.cus.tennisapp.R;
import com.tennis.cli.tenn.cus.tennisapp.RetrofitUtils.Api;

import java.time.LocalDate;
import java.time.Period;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFragment extends Fragment {


    private View view;
    private String from;
    private AppCompatImageView backImg;
    private TennisCommon tennisCommon;
    private PlayersModel playersModel;
    private String player_id;

    private CircleImageView pImg,flagImg;
    private AppCompatTextView nameHeaderTxt,nameInfoTxt,ageInfoTxt,weightTxt,heightTxt,countryTxt;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);

        playersModel = TennisApp.getSelectedPlayerModel();

        tennisCommon = new TennisCommon();

        if (playersModel != null){

            player_id  = playersModel.getPlayer_id();
        }

        System.out.println("PLAYER ID IS: "  + player_id);

        pImg = view.findViewById(R.id.profile_img);
        flagImg = view.findViewById(R.id.flag_img);


        nameHeaderTxt = view.findViewById(R.id.name_txt);
        nameInfoTxt = view.findViewById(R.id.name_txt_info);
        ageInfoTxt = view.findViewById(R.id.age_txt_info);
        heightTxt = view.findViewById(R.id.height_txt_info);
        weightTxt = view.findViewById(R.id.weight_txt_info);
        countryTxt = view.findViewById(R.id.con_txt_info);

        if (playersModel !=  null){

            if (playersModel.getGender().contentEquals("M")){
                Glide.with(getActivity()).load(getActivity().getResources().getDrawable(R.drawable.male)).into(pImg);
            }else{
                Glide.with(getActivity()).load(getActivity().getResources().getDrawable(R.drawable.female)).into(pImg);
            }
            Glide.with(getActivity()).load(getActivity().getResources().getDrawable(R.drawable.ic_flag)).into(flagImg);


        }


        backImg = view.findViewById(R.id.back);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                from = TennisApp.getFrom();

                if (MainActivity.getNavController() != null && MainActivity.getNavController().getCurrentDestination().getId() == R.id.profileFragment){

                    if (from.contentEquals("RANKING")){
                        MainActivity.getNavController().navigate(R.id.action_profileFragment_to_nav_rankings);
                    }else{
                        MainActivity.getNavController().navigate(R.id.action_profileFragment_to_sameCtyPlayersFragment);
                    }
                }

            }
        });


        loadProfileData();

        return view;
    }

    private void loadProfileData() {

        if (tennisCommon.isNetWorkAvailable(getActivity())) {

            ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Getting player data....");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(40, TimeUnit.SECONDS).readTimeout(40,TimeUnit.SECONDS).build();

            String BASE_URL =  "https://api.sportradar.com/tennis/trial/v3/en/competitors/"+player_id+"/";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Api service = retrofit.create(Api.class);

            service.profile("profile.json?api_key=z3fg5q6j88y3223ekuw39yy3").enqueue(new Callback<CprofileModel>() {
                @Override
                public void onResponse(Call<CprofileModel> call, Response<CprofileModel> response) {

                    CprofileModel cprofileModel  = response.body();

                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    if (cprofileModel != null) {

                        int age = calculateAge(cprofileModel.getInfo().getDate_of_birth());
                        System.out.println("AGE IS: " + age);

                        nameHeaderTxt.setText(cprofileModel.getCompetitor().getName());
                        nameInfoTxt.setText(cprofileModel.getCompetitor().getName());
                        ageInfoTxt.setText(age + " y.o" + " ( " + cprofileModel.getInfo().getDate_of_birth() + " )");

                        if (cprofileModel.getInfo().getHeight() != 0) {
                            heightTxt.setText(cprofileModel.getInfo().getHeight() + " cm");
                        }else{
                            heightTxt.setText("not found");
                        }

                        if (cprofileModel.getInfo().getWeight() != 0){
                            weightTxt.setText(cprofileModel.getInfo().getWeight() + " lbs");
                        }else{
                            weightTxt.setText("not found");
                        }

                        countryTxt.setText(cprofileModel.getCompetitor().getCountry());
//                    coachTxt.setText(cprofileModel.getCompetitor().getCountry());

                    }



                }

                @Override
                public void onFailure(Call<CprofileModel> call, Throwable t) {

                    if(dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    System.out.println("EXCEPTION: " + t.getMessage());
                    tennisCommon.inFormUser(getActivity(),t.getMessage());

                }
            });

        }
    }


    public static int calculateAge(String date)
    {

        String[] arr = date.split("-");
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int day = Integer.parseInt(arr[2]);

        LocalDate birthDate = LocalDate.of(year,month,day);
        LocalDate now = LocalDate.now();						//Today's date

        Period period = Period.between(birthDate, now);

       return period.getYears();


    }

}