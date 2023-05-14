package com.example.ambulancesystem.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestModel implements Parcelable {
    String id;
    String requestCase;
    Status requestStatus;
    DriverModel requestDriver;
    UserModel requestUser; //Optional
    boolean emergencyMode;
    public RequestModel() {
    }

    public RequestModel(String requestCase) {
        this.requestCase = requestCase;
    }

    public boolean isEmergencyMode() {
        return emergencyMode;
    }

    public void setEmergencyMode(boolean emergencyMode) {
        this.emergencyMode = emergencyMode;
    }

    public RequestModel(String id, Status requestStatus, DriverModel requestDriver, UserModel requestUser, boolean emergencyModel) {
        this.id = id;
        this.requestStatus = requestStatus;
        this.requestDriver = requestDriver;
        this.requestUser = requestUser;
        this.emergencyMode = emergencyModel;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "id='" + id + '\'' +
                ", requestStatus=" + requestStatus +
                ", requestDriver=" + requestDriver +
                ", requestUser=" + requestUser +
                '}';
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

    public String getRequestCase() {
        return requestCase;
    }

    public void setRequestCase(String requestCase) {
        this.requestCase = requestCase;
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
