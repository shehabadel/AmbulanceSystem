package com.example.ambulancesystem.Interfaces;

import com.example.ambulancesystem.Models.ConfirmCancelResponse;
import com.example.ambulancesystem.Models.DriverModel;
import com.example.ambulancesystem.Models.HospitalModel;
import com.example.ambulancesystem.Models.LocationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HospitalInterface {
    @GET("/hospitals")
    Call<List<HospitalModel>> getHospitals();
    @POST("/hospitals/nearest")
    Call<HospitalModel> getNearestHospital(@Body LocationModel location);
    @POST("/hospitals/confirm/{id}")
    Call<ConfirmCancelResponse> confirmRequest(@Path("id") String id);
    @POST("/hospitals/cancel/{id}")
    Call<ConfirmCancelResponse> cancelRequest(@Path("id") String id);
}
