package com.example.moduleb.data;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TimeData {
    @GET()
    Call<GsonData> getGsonData();
}
