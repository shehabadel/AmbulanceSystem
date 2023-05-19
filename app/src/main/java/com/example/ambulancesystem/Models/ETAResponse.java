package com.example.ambulancesystem.Models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ETAResponse implements Parcelable {
    private String estimatedTime;

    public ETAResponse() {
    }

    public ETAResponse(String updatedTime) {
        this.estimatedTime = updatedTime;
    }

    protected ETAResponse(Parcel in) {
        estimatedTime = in.readString();
    }

    public static final Creator<ETAResponse> CREATOR = new Creator<ETAResponse>() {
        @Override
        public ETAResponse createFromParcel(Parcel in) {
            return new ETAResponse(in);
        }

        @Override
        public ETAResponse[] newArray(int size) {
            return new ETAResponse[size];
        }
    };

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(estimatedTime);
    }
}
