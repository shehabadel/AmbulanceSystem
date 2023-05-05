package com.example.ambulancesystem.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ambulancesystem.Models.DriverModel;
import com.example.ambulancesystem.Models.RequestModel;
import com.example.ambulancesystem.Models.Status;
import com.example.ambulancesystem.Models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth auth =  FirebaseAuth.getInstance();

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

    public boolean createRequest(RequestModel createdRequest) {
        boolean isRequestCreated = pushRequest(createdRequest);
        request = new MutableLiveData<>();
        loadRequest();
        return isRequestCreated;
    }

    /**
     * Load current request created by a user
     */
    private void loadRequest() {
        try {
             String currentUser = auth.getCurrentUser().getUid();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            Query requestQuery = db.child("requests").child(currentUser);
            Log.d("query", requestQuery.toString());
            requestQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        if (snapshot.exists()) {
                            requestModel = snapshot.getValue(RequestModel.class);
                            requestModel.setRequestStatus(Status.valueOf(snapshot.child("requestStatus").getValue().toString()));
                            requestModel.setId(snapshot.getKey());
                            request.setValue(requestModel);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create request for a user
     * and driver
     */
    private boolean pushRequest(RequestModel createdRequest) {
         String currentUser = auth.getCurrentUser().getUid();
        boolean isRequestCreated = false;
        try {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            DatabaseReference requestRef = db.child("requests").child(currentUser);
            requestRef.setValue(createdRequest);
            isRequestCreated = true;
        } catch (Exception e) {
            Log.e("createRequest", e.getStackTrace().toString());
        }
        return isRequestCreated;
    }
}
