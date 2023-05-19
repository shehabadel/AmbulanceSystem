package com.example.ambulancesystem.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ambulancesystem.Models.RequestModel;
import com.example.ambulancesystem.Repository.RequestRepo;

public class RequestViewModel extends ViewModel {
    MutableLiveData<RequestModel> request;

    public void init() {
        if (request != null) {
            return;
        }
        request = RequestRepo.getInstance().getRequest();
    }

    /**
     * Get current request
     */
    public LiveData<RequestModel> getRequest() {
        return request;
    }

    /**
     * Create a request
     */
    public boolean createRequest(RequestModel requestModel) {
        return RequestRepo.getInstance().createRequest(requestModel);
    }
    /**
     * Remove a request
     * */
    public void removeRequest(){
        RequestRepo.getInstance().removeRequest();
    }
}
