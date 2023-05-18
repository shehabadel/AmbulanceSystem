package com.example.ambulancesystem.Services;

import android.util.Log;

import com.example.ambulancesystem.Interfaces.HospitalInterface;
import com.example.ambulancesystem.Models.ConfirmCancelResponse;
import com.example.ambulancesystem.Models.HospitalModel;
import com.example.ambulancesystem.Models.LocationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HospitalService {
    private static final String BASE_URL = "http://192.168.8.103:8081";
    private static Retrofit retrofit = null;
    private static HospitalInterface hospitalInterface;

    static synchronized Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        hospitalInterface = retrofit.create(HospitalInterface.class);
        return retrofit;
    }

    public HospitalInterface getHospitalInterface() {
        if (hospitalInterface == null) {
            getClient();
        }
        return hospitalInterface;
    }

    public interface HospitalsCallback {
        void onSuccess(List<HospitalModel> hospitalModels);

        void onError(String errorMessage);
    }

    /**
     * Method that fetches all hospitals
     */
    public void getAllHospitals(HospitalsCallback hospitalCallback) {
        if (hospitalInterface != null) {
            Call<List<HospitalModel>> call = hospitalInterface.getHospitals();
            call.enqueue(new Callback<List<HospitalModel>>() {
                @Override
                public void onResponse(Call<List<HospitalModel>> call, Response<List<HospitalModel>> response) {
                    if (!response.isSuccessful()) {
                        Log.d("hospitalAPI", "---Not successful");
                        hospitalCallback.onError("Request not successful");
                    } else {
                        List<HospitalModel> allHospitals = response.body();
                        for (HospitalModel hospital : allHospitals) {
                            Log.d("Hospital", "----" + hospital.getHospitalName());
                        }
                        hospitalCallback.onSuccess(allHospitals);
                    }
                }

                @Override
                public void onFailure(Call<List<HospitalModel>> call, Throwable t) {
                    Log.d("hospitalAPI", "---Call failed: " + t.getMessage());
                    hospitalCallback.onError(t.getMessage());
                }
            });
        } else {
            Log.d("hospitalAPI", "---HospitalInterface is null");
            hospitalCallback.onError("HospitalInterface is null");
        }
    }

    /**
     * Method to fetch the nearest Hospital
     */
    public void getNearestHospital(LocationModel userLocation, NearestHospitalCallback callback) {
        if (hospitalInterface != null) {
            Call<HospitalModel> call = hospitalInterface.getNearestHospital(userLocation);
            call.enqueue(new Callback<HospitalModel>() {
                @Override
                public void onResponse(Call<HospitalModel> call, Response<HospitalModel> response) {
                    if (!response.isSuccessful()) {
                        Log.d("hosptialAPIERROR",response.errorBody().toString());
                        Log.d("hospitalAPI", "---Not successful");
                        callback.onError("Hospital Request not successful");
                    } else {
                        HospitalModel nearestHospital = response.body();
                        Log.d("Hospital", "Nearest hospital: " + nearestHospital.getHospitalName());
                        callback.onSuccess(nearestHospital);
                    }
                }
                @Override
                public void onFailure(Call<HospitalModel> call, Throwable t) {
                    Log.d("hospitalAPI", "---Call failed: " + t.getMessage());
                    callback.onError(t.getMessage());
                }
            });
        } else {
            Log.d("hospitalAPI", "---HospitalInterface is null");
            callback.onError("HospitalInterface is null");
        }
    }

    /**
     * Method to confirm a request to a hospital
     */
    public void confirmRequest(String id, ConfirmRequestCallback callback) {
        if (hospitalInterface != null) {
            Call<ConfirmCancelResponse> call = hospitalInterface.confirmRequest(id);
            call.enqueue(new Callback<ConfirmCancelResponse>() {
                @Override
                public void onResponse(Call<ConfirmCancelResponse> call, Response<ConfirmCancelResponse> response) {
                    if (!response.isSuccessful()) {
                        Log.d("hospitalAPI", "---Not successful");
                        callback.onError("Request not successful");
                    } else {
                        ConfirmCancelResponse confirmationResponse = response.body();
                        String confirmationMessage = confirmationResponse.getMessage();
                        if(confirmationMessage.equals("Successful")){
                            Log.d("Hospital", "Confirmation: " + confirmationMessage);
                            callback.onSuccess(confirmationMessage);
                        }else{
                            callback.onError("Request not successful");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ConfirmCancelResponse> call, Throwable t) {
                    Log.d("hospitalAPI", "---Call failed: " + t.getMessage());
                    callback.onError(t.getMessage());
                }
            });
        } else {
            Log.d("hospitalAPI", "---HospitalInterface is null");
            callback.onError("HospitalInterface is null");
        }
    }

    /**
     * Method to cancel request
     */
    public void cancelRequest(String id, CancelRequestCallback callback) {
        if (hospitalInterface != null) {
            Call<ConfirmCancelResponse> call = hospitalInterface.cancelRequest(id);
            call.enqueue(new Callback<ConfirmCancelResponse>() {
                @Override
                public void onResponse(Call<ConfirmCancelResponse> call, Response<ConfirmCancelResponse> response) {
                    if (!response.isSuccessful()) {
                        Log.d("hospitalAPI", "---Not successful");
                        callback.onError("Request not successful");
                    } else {
                        ConfirmCancelResponse confirmationResponse = response.body();
                        String confirmationMessage = confirmationResponse.getMessage();
                        if(confirmationMessage.equals("Successful")){
                            Log.d("Hospital", "Cancellation: " + confirmationMessage);
                            callback.onSuccess(confirmationMessage);
                        }else{
                            callback.onError("Request not successful");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ConfirmCancelResponse> call, Throwable t) {
                    Log.d("hospitalAPI", "---Call failed: " + t.getMessage());
                    callback.onError(t.getMessage());
                }
            });
        } else {
            Log.d("hospitalAPI", "---HospitalInterface is null");
            callback.onError("HospitalInterface is null");
        }
    }

    public interface NearestHospitalCallback {
        void onSuccess(HospitalModel hospitalModel);

        void onError(String errorMessage);
    }

    public interface ConfirmRequestCallback {
        void onSuccess(String confirmationMessage);

        void onError(String errorMessage);
    }

    public interface CancelRequestCallback {
        void onSuccess(String confirmationMessage);

        void onError(String errorMessage);
    }
}
