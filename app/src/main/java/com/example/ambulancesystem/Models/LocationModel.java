package com.example.ambulancesystem.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class LocationModel implements Parcelable {
    public double latitude;
    public double longitude;

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
    public LocationModel(LocationModel original) {
        this.latitude = original.latitude;
        this.longitude = original.longitude;
    }

    public LocationModel() {
    }
    public LocationModel(double latitude, double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public LocationModel(double latitude, double longitude, String address, float accuracy, float speed, float bearing) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected LocationModel(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<LocationModel> CREATOR = new Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel in) {
            return new LocationModel(in);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}

