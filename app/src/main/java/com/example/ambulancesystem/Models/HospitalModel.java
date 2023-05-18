package com.example.ambulancesystem.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class HospitalModel implements Parcelable {
    @SerializedName("id")
    int hospitalID;
    @SerializedName("hospitalName")
    String hospitalName;
    @SerializedName("hospitalLocationLat")
    float hospitalLocationLat;
    @SerializedName("hospitalLocationLong")
    float hospitalLocationLong;
    @SerializedName("numberOfSeats")
    int numberOfSeats;
    LocationModel hospitalLocationModel;
    public HospitalModel(){};
    public HospitalModel(int hospitalID, String hospitalName, float hospitalLocationLat, float hospitalLocationLong, int numberOfSeats, LocationModel hospitalLocationModel) {
        this.hospitalID = hospitalID;
        this.hospitalName = hospitalName;
        this.hospitalLocationLat = hospitalLocationLat;
        this.hospitalLocationLong = hospitalLocationLong;
        this.numberOfSeats = numberOfSeats;
        this.hospitalLocationModel = hospitalLocationModel;
    }

    public int getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(int hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public float getHospitalLocationLat() {
        return hospitalLocationLat;
    }

    public void setHospitalLocationLat(float hospitalLocationLat) {
        this.hospitalLocationLat = hospitalLocationLat;
    }

    public float getHospitalLocationLong() {
        return hospitalLocationLong;
    }

    public void setHospitalLocationLong(float hospitalLocationLong) {
        this.hospitalLocationLong = hospitalLocationLong;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public LocationModel getHospitalLocation() {
        return hospitalLocationModel;
    }

    public void setHospitalLocation(LocationModel hospitalLocationModel) {
        this.hospitalLocationModel = hospitalLocationModel;
    }

    protected HospitalModel(Parcel in) {
        hospitalID = in.readInt();
        hospitalName = in.readString();
        hospitalLocationLat = in.readFloat();
        hospitalLocationLong = in.readFloat();
        numberOfSeats = in.readInt();
        hospitalLocationModel = in.readParcelable(LocationModel.class.getClassLoader());
    }

    public static final Creator<HospitalModel> CREATOR = new Creator<HospitalModel>() {
        @Override
        public HospitalModel createFromParcel(Parcel in) {
            return new HospitalModel(in);
        }

        @Override
        public HospitalModel[] newArray(int size) {
            return new HospitalModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(hospitalID);
        dest.writeString(hospitalName);
        dest.writeFloat(hospitalLocationLat);
        dest.writeFloat(hospitalLocationLong);
        dest.writeInt(numberOfSeats);
        dest.writeParcelable(hospitalLocationModel, flags);
    }
}
