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
    @SerializedName("driveCarNumber")
    String driverCarNumber;
    @SerializedName("driverPhoneNumber")
    String driverPhoneNumber;
    @SerializedName("driverStatus")
    String driverStatus;
    @SerializedName("driverLocationLong")
    float driverLocationLong;
    @SerializedName("driverLocationLat")
    float driverLocationLat;
    LocationModel driverLocationModel;

    @Override
    public String toString() {
        return "DriverModel{" +
                "driverID=" + driverID +
                ", driverName='" + driverName + '\'' +
                ", driverCarNumber='" + driverCarNumber + '\'' +
                ", driverPhoneNumber='" + driverPhoneNumber + '\'' +
                ", driverStatus='" + driverStatus + '\'' +
                ", driverLocationLong=" + driverLocationLong +
                ", driverLocationLat=" + driverLocationLat +
                '}';
    }

    public DriverModel(int driverID, String driverName, String driverCarNumber, String driverPhoneNumber, String driverStatus, float driverLocationLong, float driverLocationLat, LocationModel driverLocationModel) {
        this.driverID = driverID;
        this.driverName = driverName;
        this.driverCarNumber = driverCarNumber;
        this.driverPhoneNumber = driverPhoneNumber;
        this.driverStatus = driverStatus;
        this.driverLocationLong = driverLocationLong;
        this.driverLocationLat = driverLocationLat;
        this.driverLocationModel = driverLocationModel;
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

    public DriverModel(int driverID, String driverName, String driverCarNumber, String driverPhoneNumber, String driverStatus, LocationModel driverLocationModel) {
        this.driverID = driverID;
        this.driverName = driverName;
        this.driverCarNumber = driverCarNumber;
        this.driverPhoneNumber = driverPhoneNumber;
        this.driverStatus = driverStatus;
        this.driverLocationModel = driverLocationModel;
    }

    public DriverModel(String driverName, String driverCarNumber, String driverPhoneNumber, String driverStatus, LocationModel driverLocationModel) {
        this.driverName = driverName;
        this.driverCarNumber = driverCarNumber;
        this.driverPhoneNumber = driverPhoneNumber;
        this.driverStatus = driverStatus;
        this.driverLocationModel = driverLocationModel;
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

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public LocationModel getDriverLocation() {
        return driverLocationModel;
    }

    public void setDriverLocation(LocationModel driverLocationModel) {
        this.driverLocationModel = driverLocationModel;
    }

    protected DriverModel(Parcel in) {
        driverName = in.readString();
        driverCarNumber = in.readString();
        driverPhoneNumber = in.readString();
        driverStatus = in.readString();
        driverLocationModel = in.readParcelable(LocationModel.class.getClassLoader());
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
        dest.writeString(driverStatus);
        dest.writeParcelable(driverLocationModel, flags);
    }
}
