package com.example.ambulancesystem.Services;

import com.example.ambulancesystem.Interfaces.DriverInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverService {
    private static final String BASE_URL = "http://192.168.8.104:8080";
    private static Retrofit retrofit = null;
    private static DriverInterface driverInterface;
    static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        driverInterface=retrofit.create(DriverInterface.class);
        return retrofit;
    }
    public DriverInterface getDriverInterface() {
        if (driverInterface == null) {
            getClient();
        }
        return driverInterface;
    }
}
