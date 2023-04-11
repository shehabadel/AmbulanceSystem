package com.example.ambulancesystem.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Address implements Parcelable {
    String city;
    String streetName;
    int buildNumber;
    int floorNumber;
    int appNumber;

    protected Address(Parcel in) {
        city = in.readString();
        streetName = in.readString();
        buildNumber = in.readInt();
        floorNumber = in.readInt();
        appNumber = in.readInt();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(int appNumber) {
        this.appNumber = appNumber;
    }


    public Address(String city, String streetName, int buildNumber, int floorNumber, int appNumber) {
        this.city = city;
        this.streetName = streetName;
        this.buildNumber = buildNumber;
        this.floorNumber = floorNumber;
        this.appNumber = appNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(streetName);
        dest.writeInt(buildNumber);
        dest.writeInt(floorNumber);
        dest.writeInt(appNumber);
    }
}
