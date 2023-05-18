package com.example.ambulancesystem.Interfaces;
import com.example.ambulancesystem.Models.ETAModel;
import com.example.ambulancesystem.Models.ETAResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface ETAInterface {
    @POST("/ETA")
    Call<ETAResponse> getETA(@Body ETAModel etaModel);
}
