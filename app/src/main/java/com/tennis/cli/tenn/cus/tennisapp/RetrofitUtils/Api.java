package com.tennis.cli.tenn.cus.tennisapp.RetrofitUtils;

import com.tennis.cli.tenn.cus.tennisapp.Models.Profile.CprofileModel;
import com.tennis.cli.tenn.cus.tennisapp.Models.RankingsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {

    @GET
    Call<RankingsModel> rankings(@Url String url);

    @GET
    Call<CprofileModel> profile(@Url String url);

}
