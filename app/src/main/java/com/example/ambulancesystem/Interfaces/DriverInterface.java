package com.example.ambulancesystem.Interfaces;

import com.example.ambulancesystem.Models.ConfirmCancelResponse;
import com.example.ambulancesystem.Models.DriverModel;
import com.example.ambulancesystem.Models.LocationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DriverInterface {
    @GET("/drivers")
    Call<List<DriverModel>>getDrivers();
    @POST("/drivers/nearest")
    Call<DriverModel> getNearestDriver(@Body LocationModel location);
    @POST("/drivers/confirm/{id}")
    Call<ConfirmCancelResponse> confirmRequest(@Path("id") String id);
    @POST("/drivers/cancel/{id}")
    Call<ConfirmCancelResponse> cancelRequest(@Path("id") String id);

}
//http://localhost:8080/drivers/confirm/4