package com.example.ambulancesystem.Services;

import android.util.Log;

import com.example.ambulancesystem.Interfaces.DriverInterface;
import com.example.ambulancesystem.Models.ConfirmCancelResponse;
import com.example.ambulancesystem.Models.DriverModel;
import com.example.ambulancesystem.Models.LocationModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverService {
    private static final String BASE_URL = "http://192.168.1.8:8080";
    private static Retrofit retrofit = null;
    private static DriverInterface driverInterface;

    static synchronized Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        driverInterface = retrofit.create(DriverInterface.class);
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

    /**
     * Method that fetches all drivers
     */
    public void getAllDrivers(DriversCallback driverCallback) {
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

    /**
     * Method to fetch the nearest Driver
     */
    public void getNearestDriver(LocationModel userLocation, NearestDriverCallback callback) {
        if (driverInterface != null) {
            Call<DriverModel> call = driverInterface.getNearestDriver(userLocation);
            call.enqueue(new Callback<DriverModel>() {
                @Override
                public void onResponse(Call<DriverModel> call, Response<DriverModel> response) {
                    if (!response.isSuccessful()) {
                        Log.d("driverAPI", "---Not successful");
                        callback.onError("Request not successful");
                    } else {
                        try {
                            DriverModel nearestDriver = response.body();
                            Log.d("Driver", "Nearest driver: " + nearestDriver.getDriverName());
                            callback.onSuccess(nearestDriver);
                        } catch (Exception e) {
                            Log.d("DriverResError:", String.valueOf(response.body()));
                            e.printStackTrace();
                            callback.onError(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<DriverModel> call, Throwable t) {
                    Log.d("driverAPI", "---Call failed: " + t.getMessage());
                    callback.onError(t.getMessage());
                }
            });
        } else {
            Log.d("driverAPI", "---DriverInterface is null");
            callback.onError("DriverInterface is null");
        }
    }

    /**
     * Method to confirm a request
     */
    public void confirmRequest(String id, ConfirmRequestCallback callback) {
        if (driverInterface != null) {
            Call<ConfirmCancelResponse> call = driverInterface.confirmRequest(id);
            call.enqueue(new Callback<ConfirmCancelResponse>() {
                @Override
                public void onResponse(Call<ConfirmCancelResponse> call, Response<ConfirmCancelResponse> response) {
                    if (!response.isSuccessful()) {
                        Log.d("driverAPI", "---Not successful");
                        callback.onError("Request not successful");
                    } else {
                        ConfirmCancelResponse confirmationResponse = response.body();
                        String confirmationMessage = confirmationResponse.getMessage();
                        Log.d("confirmMessage",confirmationMessage);
                        if(confirmationMessage.equals("Successful")){
                            Log.d("Driver", "Confirmation: " + confirmationMessage);
                            callback.onSuccess(confirmationMessage);
                        }else{
                            callback.onError("Request not successful");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ConfirmCancelResponse> call, Throwable t) {
                    Log.d("driverAPI", "---Call failed: " + t.getMessage());
                    callback.onError(t.getMessage());
                }
            });
        } else {
            Log.d("driverAPI", "---DriverInterface is null");
            callback.onError("DriverInterface is null");
        }
    }

    /**
     * Method to cancel a request
     */
    public void cancelRequest(String id, CancelRequestCallback callback) {
        if (driverInterface != null) {
            Call<ConfirmCancelResponse> call = driverInterface.cancelRequest(id);
            call.enqueue(new Callback<ConfirmCancelResponse>() {
                @Override
                public void onResponse(Call<ConfirmCancelResponse> call, Response<ConfirmCancelResponse> response) {
                    if (!response.isSuccessful()) {
                        Log.d("driverAPI", "---Not successful");
                        callback.onError("Request not successful");
                    } else {
                        ConfirmCancelResponse confirmationResponse = response.body();
                        String confirmationMessage = confirmationResponse.getMessage();
                        if(confirmationMessage.equals("Successful")){
                            Log.d("Driver", "Cancellation: " + confirmationMessage);
                            callback.onSuccess(confirmationMessage);
                        }else{
                            callback.onError("Request not successful");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ConfirmCancelResponse> call, Throwable t) {
                    Log.d("driverAPI", "---Call failed: " + t.getMessage());
                    callback.onError(t.getMessage());
                }
            });
        } else {
            Log.d("driverAPI", "---DriverInterface is null");
            callback.onError("DriverInterface is null");
        }
    }

    public interface ConfirmRequestCallback {
        void onSuccess(String confirmationMessage);

        void onError(String errorMessage);
    }

    public interface CancelRequestCallback {
        void onSuccess(String confirmationMessage);

        void onError(String errorMessage);
    }

    public interface NearestDriverCallback {
        void onSuccess(DriverModel driverModel);

        void onError(String errorMessage);
    }
}
