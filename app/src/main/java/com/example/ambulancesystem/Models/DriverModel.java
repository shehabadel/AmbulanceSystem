package com.example.ambulancesystem.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class DriverModel implements Parcelable {
    @SerializedName("driverName")
    String driverName;
    @SerializedName("driverCarNumber")
    String driverCarNumber;
    @SerializedName("driverPhoneNumber")
    String driverPhoneNumber;
    @SerializedName("driverETA")
    String driverEstimatedTime;
    @SerializedName("driverLocation")
    Location driverLocation;

    public DriverModel() {
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
