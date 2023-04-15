package com.example.ambulancesystem.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ambulancesystem.Models.RequestModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Create a request for a driver (depend on DriverRepo)
 * Fetch current requests.
 * */
public class RequestRepo {
    static RequestRepo instance;
    private MutableLiveData<RequestModel> request;
    RequestModel requestModel = new RequestModel();

    public static RequestRepo getInstance(){
        if(instance==null){
            instance = new RequestRepo();
        }
        return instance;
    }

    public MutableLiveData<RequestModel> getRequest(){
        request = new MutableLiveData<>();
        //loadRequest();
        request.setValue(requestModel);
        return request;
    }
    private void loadRequest(){
        try{
        // String currentUser = auth.getCurrentUser().getUid();
        String currentUser = "A";
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query requestQuery = db.child("requests").child(currentUser).child("profile");
    }catch(Exception e){
        Log.e("loadUser",e.getMessage());
    }
    }
}
