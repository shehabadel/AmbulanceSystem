package com.example.ambulancesystem.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UserModel implements Parcelable {
    String userID;
    String firstName;
    String lastName;
    String medicalCondition;
    String dateOfBirth;
    String phoneNumber;
    String gender;
    String nationalID;
    Address pickupAddress;
    Location currentLocation;

    public UserModel(String userID,
                     String firstName,
                     String lastName,
                     String medicalCondition,
                     String dateOfBirth,
                     String phoneNumber,
                     String gender,
                     String nationalID,
                     Address pickupAddress,
                     Location currentLocation) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.medicalCondition = medicalCondition;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.nationalID = nationalID;
        this.pickupAddress = pickupAddress;
        this.currentLocation = currentLocation;
    }

    protected UserModel(Parcel in) {
        userID = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        medicalCondition = in.readString();
        dateOfBirth = in.readString();
        phoneNumber = in.readString();
        gender = in.readString();
        nationalID = in.readString();
        pickupAddress = in.readParcelable(Address.class.getClassLoader());
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public Address getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(Address pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(userID);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(medicalCondition);
        dest.writeString(dateOfBirth);
        dest.writeString(phoneNumber);
        dest.writeString(gender);
        dest.writeString(nationalID);
        dest.writeParcelable(pickupAddress, flags);
    }
}
