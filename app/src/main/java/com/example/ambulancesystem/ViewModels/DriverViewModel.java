package com.example.ambulancesystem.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ambulancesystem.Models.DriverModel;
import com.example.ambulancesystem.Repository.DriverRepo;

public class DriverViewModel extends ViewModel {
    MutableLiveData<DriverModel> driver;
    public void init(){
        if(driver!=null){
            return;
        }
        //driver = DriverRepo.getInstance().getDriver();
    }
}
