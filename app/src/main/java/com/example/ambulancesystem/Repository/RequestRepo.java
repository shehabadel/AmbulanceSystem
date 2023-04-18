package com.example.ambulancesystem.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ambulancesystem.Models.RequestModel;
import com.example.ambulancesystem.Models.Status;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Create a request for a driver (depend on DriverRepo)
 * Fetch current requests.
 */
public class RequestRepo {
    static RequestRepo instance;
    private MutableLiveData<RequestModel> request;
    RequestModel requestModel = new RequestModel();

    public static RequestRepo getInstance() {
        if (instance == null) {
            instance = new RequestRepo();
        }
        return instance;
    }

    public MutableLiveData<RequestModel> getRequest() {
        request = new MutableLiveData<>();
        loadRequest();
        request.setValue(requestModel);
        return request;
    }

    private void loadRequest() {
        try {
            // String currentUser = auth.getCurrentUser().getUid();
            String currentUser = "A";
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            Query requestQuery = db.child("requests").child(currentUser);
            Log.d("query", requestQuery.toString());
            requestQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        Log.d("Request", "Status");
                        if (snapshot.exists()) {
                            requestModel = snapshot.getValue(RequestModel.class);
                            Log.d("RequestStatus", snapshot.child("requestStatus").getValue().toString());
                            requestModel.setRequestStatus(Status.valueOf(snapshot.child("requestStatus").getValue().toString()));
                            requestModel.setId(snapshot.getKey());
                            request.setValue(requestModel);
                        }
                    } catch (Exception e) {
                        Log.e("loadRequestSnapshot", e.getMessage());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } catch (Exception e) {
            Log.e("loadRequest", e.getMessage());
        }
    }
}
