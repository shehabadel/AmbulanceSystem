package com.example.ambulancesystem.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ambulancesystem.Models.Address;
import com.example.ambulancesystem.Models.LocationModel;
import com.example.ambulancesystem.Models.UserModel;
import com.example.ambulancesystem.Repository.UserRepo;

public class UserViewModel extends ViewModel {
    MutableLiveData<UserModel> user;

    public void init() {
        if (user != null) {
            return;
        }
        user = UserRepo.getInstance().getUser();
    }

    /**
     * Fetch user's profile data including
     * saved pickupAddress and current location
     */
    public LiveData<UserModel> getUser() {
        return user;
    }

    /**
     * Post/Update user's pickup address
     */
    public boolean updateUserAddress(Address address) {
        return UserRepo.getInstance().updatePickupAddress(address);
    }

    /**
     * set user's profile details including
     * pickupAddress and current Location
     */
    public void setProfile(UserModel userDetails) {
        UserRepo.getInstance().setProfile(userDetails);
    }
    /**
     * Update user's profile details including
     * pickupAddress and current Location
     */
    public void updateProfile(UserModel userDetails) {
        UserRepo.getInstance().updateProfile(userDetails);
    }
    /**
     * Update user's current location
     */
    public boolean updateUserLocation(LocationModel locationModel) {
        return UserRepo.getInstance().updateCurrentLocation(locationModel);
    }
}
