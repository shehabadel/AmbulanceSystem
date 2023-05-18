package com.example.ambulancesystem.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ambulancesystem.Models.Address;
import com.example.ambulancesystem.Models.LocationModel;
import com.example.ambulancesystem.Models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * UserRepo needed to save user's profile data,
 * fetch user's profile data, fetch current location
 * update current location.
 */
public class UserRepo {
    static UserRepo instance;
    private MutableLiveData<UserModel> user = new MutableLiveData<>();
    UserModel userModel = new UserModel();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public static UserRepo getInstance() {
        if (instance == null) {
            instance = new UserRepo();
        }
        return instance;
    }

    public MutableLiveData<UserModel> getUser() {
//        user = new MutableLiveData<>();
        userModel = new UserModel();
        loadUser();
        user.setValue(userModel);
        return user;
    }

    public boolean updatePickupAddress(Address address) {
        return updateAddress(address);
    }

    public void setProfile(UserModel userDetails) {
        setProfileAUX(userDetails);
        loadUser();
    }

    public void updateProfile(UserModel userDetails) {
        updateProfileAUX(userDetails);
        loadUser();
    }

    public boolean updateCurrentLocation(LocationModel locationModel) {
        boolean isUserLocationUpdated = updateUserLocation(locationModel);
        return isUserLocationUpdated;
    }

    /**
     * Fetch user's data from users node
     * based on current user uuid
     */
    private void loadUser() {
        try {
            String currentUser = auth.getCurrentUser().getUid();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            Query userQuery = db.child("users").child(currentUser).child("profile");
            Log.d("user", userQuery.toString());

            userQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Log.d("snapshot", snapshot.getValue().toString());
                        userModel = snapshot.getValue(UserModel.class);
                        Log.d("userSnapshot", userModel.toString());
                        Address userAddress = snapshot.child("pickupAddress").getValue(Address.class);
                        if (userAddress != null) {
                            Log.d("userAddress", userAddress.toString());
                            userModel.setPickupAddress(userAddress);
                        }
                        userModel.setUserID(currentUser);
                        user.setValue(userModel);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            Log.e("loadUser", e.getMessage());
        }
    }

    /**
     * set user's profile
     */
    private void setProfileAUX(UserModel userDetails) {
        String currentUser = auth.getCurrentUser().getUid();
        try {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userProfile = db.child("users").child(currentUser).child("profile");
            userProfile.setValue(userDetails);
            DatabaseReference userAddress = userProfile.child("pickupAddress");
            DatabaseReference userLocation = userProfile.child("currentLocation");

            if (userDetails.getPickupAddress() != null) {
                userAddress.setValue(userDetails.getPickupAddress());
            }
            if (userDetails.getCurrentLocation() != null) {
                userLocation.setValue(userDetails.getCurrentLocation());
            }
        } catch (Exception e) {
            Log.e("updateProfile", e.getMessage());
            Log.e("updateprofstack", e.getStackTrace().toString());
            e.printStackTrace();
        }
    }

    /**
     * Update user's profile
     */
    private void updateProfileAUX(UserModel userDetails) {
        String currentUser = auth.getCurrentUser().getUid();
        try {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userProfile = db.child("users").child(currentUser).child("profile");
            HashMap<String, Object> updates = new HashMap<>();

            // Get all the fields of the UserModel class using reflection
            Field[] fields = UserModel.class.getDeclaredFields();

            for (Field field : fields) {

                // Get the name of the field
                String fieldName = field.getName();

                // Skip the "CREATOR" field
                if (fieldName.equals("CREATOR")) {
                    continue;
                }
                // Get the value of the field from the userDetails object
                field.setAccessible(true);
                Object fieldValue = field.get(userDetails);

                // Add the field and its value to the updates HashMap
                if (fieldValue != null) {
                    updates.put(fieldName, fieldValue);
                }

            }
            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                Log.d("Update", key + ": " + value);
            }

            userProfile.updateChildren(updates);
        } catch (Exception e) {
            Log.e("updateProfile", e.getMessage());
            Log.e("updateprofstack", e.getStackTrace().toString());
            e.printStackTrace();
        }
    }

    /**
     * Update user's pickup address
     * from users node based on current uuid
     */
    private boolean updateAddress(Address address) {
        String currentUser = auth.getCurrentUser().getUid();
        boolean updatedAddress = false;
        try {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userPickupAddressRef = db.child("users").child(currentUser).child("profile").child("pickupAddress");
            userPickupAddressRef.setValue(address);
            updatedAddress = true;
        } catch (Exception e) {
            Log.e("updateAddr", e.getMessage());
        }
        return updatedAddress;
    }

    /**
     * Update's users current Location
     */
    private boolean updateUserLocation(LocationModel locationModel) {
        String currentUser = auth.getCurrentUser().getUid();
        boolean userLocation = false;
        try {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userPickupLocationRef = db.child("users").child(currentUser).child("profile").child("currentLocation");
            userPickupLocationRef.setValue(locationModel);
            userLocation = true;
        } catch (Exception e) {
            Log.e("updateLocation", e.getMessage());
        }
        return userLocation;
    }
    /**
     * Update
     * */
}
