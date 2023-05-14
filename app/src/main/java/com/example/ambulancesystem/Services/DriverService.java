package com.example.ambulancesystem.Services;

import android.util.Log;

import com.example.ambulancesystem.Interfaces.DriverInterface;
import com.example.ambulancesystem.Models.DriverModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverService {
    private static final String BASE_URL = "http://192.168.8.103:8080";
    private static Retrofit retrofit = null;
    private static DriverInterface driverInterface;
    static synchronized Retrofit getClient() {
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
    public interface DriversCallback {
        void onSuccess(List<DriverModel> driverModels);
        void onError(String errorMessage);
    }

    public void getAllDrivers(DriversCallback driverCallback){
        if (driverInterface != null) {
            Call<List<DriverModel>> call = driverInterface.getDrivers();
            call.enqueue(new Callback<List<DriverModel>>() {
                @Override
                public void onResponse(Call<List<DriverModel>> call, Response<List<DriverModel>> response) {
                    if (!response.isSuccessful()) {
                        Log.d("driverAPI", "---Not successful");
                        driverCallback.onError("Request not successful");
                    } else {
                        List<DriverModel> allDrivers = response.body();
                        for (DriverModel driver : allDrivers) {
                            Log.d("Driveaas", "----" + driver.getDriverName());
                        }
                        driverCallback.onSuccess(allDrivers);
                    }
                }

                @Override
                public void onFailure(Call<List<DriverModel>> call, Throwable t) {
                    Log.d("driverAPI", "---Call failed: " + t.getMessage());
                    driverCallback.onError(t.getMessage());
                }
            });
        } else {
            Log.d("driverAPI", "---DriverInterface is null");
            driverCallback.onError("DriverInterface is null");
        }
    }

}
