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
    String driveCarNumber;
    @SerializedName("driverPhoneNumber")
    String driverPhoneNumber;
    @SerializedName("driverStatus")
    String driverStatus;
    @SerializedName("driverLocationLong")
    float driverLocationLong;
    @SerializedName("driverLocationLat")
    float driverLocationLat;


    protected DriverModel(Parcel in) {
        driverID = in.readInt();
        driverName = in.readString();
        driveCarNumber = in.readString();
        driverPhoneNumber = in.readString();
        driverStatus = in.readString();
        driverLocationLong = in.readFloat();
        driverLocationLat = in.readFloat();
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
    public String toString() {
        return "DriverModel{" +
                "driverID=" + driverID +
                ", driverName='" + driverName + '\'' +
                ", driverCarNumber='" + driveCarNumber + '\'' +
                ", driverPhoneNumber='" + driverPhoneNumber + '\'' +
                ", driverStatus='" + driverStatus + '\'' +
                ", driverLocationLong=" + driverLocationLong +
                ", driverLocationLat=" + driverLocationLat +
                '}';
    }

    public DriverModel(int driverID, String driverName, String driveCarNumber, String driverPhoneNumber, String driverStatus, float driverLocationLong, float driverLocationLat) {
        this.driverID = driverID;
        this.driverName = driverName;
        this.driveCarNumber = driveCarNumber;
        this.driverPhoneNumber = driverPhoneNumber;
        this.driverStatus = driverStatus;
        this.driverLocationLong = driverLocationLong;
        this.driverLocationLat = driverLocationLat;
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

    public DriverModel(int driverID, String driverName, String driveCarNumber, String driverPhoneNumber, String driverStatus) {
        this.driverID = driverID;
        this.driverName = driverName;
        this.driveCarNumber = driveCarNumber;
        this.driverPhoneNumber = driverPhoneNumber;
        this.driverStatus = driverStatus;
    }

    public DriverModel(String driverName, String driveCarNumber, String driverPhoneNumber, String driverStatus) {
        this.driverName = driverName;
        this.driveCarNumber = driveCarNumber;
        this.driverPhoneNumber = driverPhoneNumber;
        this.driverStatus = driverStatus;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriveCarNumber() {
        return driveCarNumber;
    }

    public void setDriveCarNumber(String driveCarNumber) {
        this.driveCarNumber = driveCarNumber;
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


    public void setDriverLocation(LocationModel driverLocationModel) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(driverID);
        dest.writeString(driverName);
        dest.writeString(driveCarNumber);
        dest.writeString(driverPhoneNumber);
        dest.writeString(driverStatus);
        dest.writeFloat(driverLocationLong);
        dest.writeFloat(driverLocationLat);
    }
}
