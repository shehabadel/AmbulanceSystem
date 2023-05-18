package com.example.ambulancesystem.Services;

import android.util.Log;

import com.example.ambulancesystem.Interfaces.DriverInterface;
import com.example.ambulancesystem.Interfaces.ETAInterface;
import com.example.ambulancesystem.Models.DriverModel;
import com.example.ambulancesystem.Models.ETAModel;
import com.example.ambulancesystem.Models.ETAResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ETAService
{
    private static final String BASE_URL = "http://192.168.8.103:8082";
    private static Retrofit retrofit = null;
    private static ETAInterface etaInterface;
    static synchronized Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        etaInterface = retrofit.create(ETAInterface.class);
        return retrofit;
    }
    public ETAInterface getETAInterface() {
        if (etaInterface == null) {
            getClient();
        }
        return etaInterface;
    }
    public void getETA(ETAModel etaModel,ETACallback etaCallback) {
        if (etaInterface != null) {
            Call<ETAResponse> call = etaInterface.getETA(etaModel);
            call.enqueue(new Callback<ETAResponse>() {
                @Override
                public void onResponse(Call<ETAResponse> call, Response<ETAResponse> response) {
                    if (!response.isSuccessful()) {
                        Log.d("ETAAPI", "---Not successful");
                        etaCallback.onError("ETAAPI-Request not successful");
                    } else {
                        try {
                            ETAResponse etaResponse = response.body();
                            etaCallback.onSuccess(etaResponse);
                        } catch (Exception e) {
                            Log.d("ETAResponseError:", String.valueOf(response.body()));
                            e.printStackTrace();
                            etaCallback.onError(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ETAResponse> call, Throwable t) {
                    Log.d("ETAAPI", "---Call failed: " + t.getMessage());
                    etaCallback.onError(t.getMessage());
                }
            });
        } else {
            Log.d("ETAAPI", "---ETAInterface is null");
            etaCallback.onError("ETAInterface is null");
        }
    }

    public interface ETACallback {
        void onSuccess(ETAResponse etaResponse);

        void onError(String errorMessage);
    }
}
