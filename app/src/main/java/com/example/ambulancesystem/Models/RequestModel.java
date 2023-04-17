package com.example.ambulancesystem.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestModel implements Parcelable {
    String id;
    Status requestStatus;
    DriverModel requestDriver;
    UserModel requestUser; //Optional

    public RequestModel() {
    }

    public RequestModel(String id, Status requestStatus, DriverModel requestDriver, UserModel requestUser) {
        this.id = id;
        this.requestStatus = requestStatus;
        this.requestDriver = requestDriver;
        this.requestUser = requestUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RequestModel(Status requestStatus, DriverModel requestDriver, UserModel requestUser) {
        this.requestStatus = requestStatus;
        this.requestDriver = requestDriver;
        this.requestUser = requestUser;
    }

    public RequestModel(Status requestStatus, DriverModel requestDriver) {
        this.requestStatus = requestStatus;
        this.requestDriver = requestDriver;
    }

    protected RequestModel(Parcel in) {
        requestDriver = in.readParcelable(DriverModel.class.getClassLoader());
        requestUser = in.readParcelable(UserModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(requestDriver, flags);
        dest.writeParcelable(requestUser, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RequestModel> CREATOR = new Creator<RequestModel>() {
        @Override
        public RequestModel createFromParcel(Parcel in) {
            return new RequestModel(in);
        }

        @Override
        public RequestModel[] newArray(int size) {
            return new RequestModel[size];
        }
    };

    public Status getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Status requestStatus) {
        this.requestStatus = requestStatus;
    }

    public DriverModel getRequestDriver() {
        return requestDriver;
    }

    public void setRequestDriver(DriverModel requestDriver) {
        this.requestDriver = requestDriver;
    }

    public UserModel getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(UserModel requestUser) {
        this.requestUser = requestUser;
    }
}
