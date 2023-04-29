package com.example.ambulancesystem.Interfaces;

import com.example.ambulancesystem.Models.DriverModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DriverInterface {
    @GET("/drivers")
    Call<List<DriverModel>>getDrivers();
}
