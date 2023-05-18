package com.example.ambulancesystem.Models;

public class ETAModel{
    private double userLongitude;
    private double userLatitude;
    private double driverLongitude;
    private double driverLatitude;

    @Override
    public String toString() {
        return "ETAModel{" +
                "userLong=" + userLongitude +
                ", userLat=" + userLatitude +
                ", driverLong=" + driverLongitude +
                ", driverLat=" + driverLatitude +
                '}';
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public double getDriverLongitude() {
        return driverLongitude;
    }

    public void setDriverLongitude(double driverLongitude) {
        this.driverLongitude = driverLongitude;
    }

    public double getDriverLatitude() {
        return driverLatitude;
    }

    public void setDriverLatitude(double driverLatitude) {
        this.driverLatitude = driverLatitude;
    }
}
