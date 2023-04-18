package com.example.ambulancesystem.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ambulancesystem.Models.Address;
import com.example.ambulancesystem.Models.UserModel;
import com.example.ambulancesystem.Repository.UserRepo;

public class UserViewModel extends ViewModel {
    MutableLiveData<UserModel> user;
    public void init(){
        if(user!=null){
            return;
        }
        user = UserRepo.getInstance().getUser();
    }
    public LiveData<UserModel> getUser(){
        return user;
    }
    public void updateUserAddress(Address address){
        //Call user's repo to update user's address
    }
}
