package com.example.belajar_sharedprefence.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {
    @GET("pray/{cityName}")
    Call<ResponseBody> getJadwalSholat(@Path("cityName")String cityName);
}
