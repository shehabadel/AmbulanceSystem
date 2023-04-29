package com.example.ambulancesystem.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class DriverModel implements Parcelable {
    @SerializedName("id")
    int driverID;
    @SerializedName("driverName")
    String driverName;
    @SerializedName("driverCarNumber")
    String driverCarNumber;
    @SerializedName("driverPhoneNumber")
    String driverPhoneNumber;
    @SerializedName("driverETA")
    String driverEstimatedTime;
    @SerializedName("driverLocationLong")
    float driverLocationLong;
    @SerializedName("driverLocationLat")
    float driverLocationLat;
    Location driverLocation;

    public DriverModel(int driverID, String driverName, String driverCarNumber, String driverPhoneNumber, String driverEstimatedTime, float driverLocationLong, float driverLocationLat, Location driverLocation) {
        this.driverID = driverID;
        this.driverName = driverName;
        this.driverCarNumber = driverCarNumber;
        this.driverPhoneNumber = driverPhoneNumber;
        this.driverEstimatedTime = driverEstimatedTime;
        this.driverLocationLong = driverLocationLong;
        this.driverLocationLat = driverLocationLat;
        this.driverLocation = driverLocation;
    }

    public float getDriverLocationLong() {
        return driverLocationLong;
    }

    public void setDriverLocationLong(float driverLocationLong) {
        this.driverLocationLong = driverLocationLong;
    }

    public float getDriverLocationLat() {
        return driverLocationLat;
    }

    public void setDriverLocationLat(float driverLocationLat) {
        this.driverLocationLat = driverLocationLat;
    }

    public DriverModel() {
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public DriverModel(int driverID, String driverName, String driverCarNumber, String driverPhoneNumber, String driverEstimatedTime, Location driverLocation) {
        this.driverID = driverID;
        this.driverName = driverName;
        this.driverCarNumber = driverCarNumber;
        this.driverPhoneNumber = driverPhoneNumber;
        this.driverEstimatedTime = driverEstimatedTime;
        this.driverLocation = driverLocation;
    }

    public DriverModel(String driverName, String driverCarNumber, String driverPhoneNumber, String driverEstimatedTime, Location driverLocation) {
        this.driverName = driverName;
        this.driverCarNumber = driverCarNumber;
        this.driverPhoneNumber = driverPhoneNumber;
        this.driverEstimatedTime = driverEstimatedTime;
        this.driverLocation = driverLocation;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverCarNumber() {
        return driverCarNumber;
    }

    public void setDriverCarNumber(String driverCarNumber) {
        this.driverCarNumber = driverCarNumber;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public String getDriverEstimatedTime() {
        return driverEstimatedTime;
    }

    public void setDriverEstimatedTime(String driverEstimatedTime) {
        this.driverEstimatedTime = driverEstimatedTime;
    }

    public Location getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(Location driverLocation) {
        this.driverLocation = driverLocation;
    }

    protected DriverModel(Parcel in) {
        driverName = in.readString();
        driverCarNumber = in.readString();
        driverPhoneNumber = in.readString();
        driverEstimatedTime = in.readString();
        driverLocation = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Creator<DriverModel> CREATOR = new Creator<DriverModel>() {
        @Override
        public DriverModel createFromParcel(Parcel in) {
            return new DriverModel(in);
        }

        @Override
        public DriverModel[] newArray(int size) {
            return new DriverModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(driverName);
        dest.writeString(driverCarNumber);
        dest.writeString(driverPhoneNumber);
        dest.writeString(driverEstimatedTime);
        dest.writeParcelable(driverLocation, flags);
    }
}
