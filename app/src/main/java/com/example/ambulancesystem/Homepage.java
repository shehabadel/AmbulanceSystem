package com.example.ambulancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ambulancesystem.Interfaces.DriverInterface;
import com.example.ambulancesystem.Interfaces.ETAInterface;
import com.example.ambulancesystem.Interfaces.HospitalInterface;
import com.example.ambulancesystem.Models.DriverModel;
import com.example.ambulancesystem.Models.ETAModel;
import com.example.ambulancesystem.Models.ETAResponse;
import com.example.ambulancesystem.Models.HospitalModel;
import com.example.ambulancesystem.Models.LocationModel;
import com.example.ambulancesystem.Models.RequestModel;
import com.example.ambulancesystem.Models.Status;
import com.example.ambulancesystem.Services.DriverService;
import com.example.ambulancesystem.Services.ETAService;
import com.example.ambulancesystem.Services.HospitalService;
import com.example.ambulancesystem.Services.LocationService;
import com.example.ambulancesystem.ViewModels.RequestViewModel;
import com.example.ambulancesystem.ViewModels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity {

    Switch emergencySwitch;
    TextView requestAmbulanceButton;
    ImageButton logoutButton;
    RelativeLayout accountButton;
    private static final int PERMISSIONS_REQUEST_LOCATION = 99;
    private LocationService locationService;
    private boolean bound = false;
    Location currentLocation;
    UserViewModel userViewModel;
    DriverInterface driverAPI;
    DriverService driverService;
    RequestViewModel requestViewModel;
    LocationModel userLocationModel;
    DriverModel requestedDriver;
    HospitalInterface hospitalAPI;
    HospitalService hospitalService;
    HospitalModel requestedHospital;
    ETAService etaService;
    ETAInterface ETAAPI;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_home_page);
        //APIs
        checkLocationPermission();
        //Fetches the user's current location
        fetchUserLocation();
        //ViewModel to deal with Firebase User node
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init();
        //VIewModel to deal with Firebase Request node
        requestViewModel = new ViewModelProvider(this).get(RequestViewModel.class);
        requestViewModel.init();
        //API driver Service
        driverService = new DriverService();
        driverAPI = driverService.getDriverInterface();
        //API Hospital Service
        hospitalService = new HospitalService();
        hospitalAPI = hospitalService.getHospitalInterface();
        //API ETA Service
        etaService = new ETAService();
        ETAAPI = etaService.getETAInterface();
        //UI Declarations
        emergencySwitch = findViewById(R.id.emergencySwitch);
        requestAmbulanceButton = findViewById(R.id.requestAmbulanceButton);
        logoutButton = findViewById(R.id.logoutButton);
        accountButton = findViewById(R.id.accountButtonHome);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Homepage.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        requestAmbulanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    try {
                        if (currentLocation != null) {
                            userLocationModel = new LocationModel(currentLocation.getLatitude(), currentLocation.getLongitude());
                            //Save user's location
                            userViewModel.updateUserLocation(userLocationModel);
                            //Get nearest driver
                            driverService.getNearestDriver(userLocationModel, new DriverService.NearestDriverCallback() {
                                @Override
                                public void onSuccess(DriverModel driverModel) {
                                    Log.d("DRIVRERER", driverModel.toString());
                                    requestedDriver = new DriverModel();
                                    requestedDriver.setDriverID(driverModel.getDriverID());
                                    requestedDriver.setDriverName(driverModel.getDriverName());
                                    requestedDriver.setDriverStatus(driverModel.getDriverStatus());
                                    requestedDriver.setDriveCarNumber(driverModel.getDriveCarNumber());
                                    requestedDriver.setDriverPhoneNumber(driverModel.getDriverPhoneNumber());
                                    requestedDriver.setDriverLocationLat(driverModel.getDriverLocationLat());
                                    requestedDriver.setDriverLocationLong(driverModel.getDriverLocationLong());
                                    //Show medical request popup
                                    showMedicalCasePopup();
                                }

                                @Override
                                public void onError(String errorMessage) {
                                    Log.d("drivererror", errorMessage);
                                    Toast.makeText(getApplicationContext(), "DriverAPI: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Please wait until the location works", Toast.LENGTH_SHORT).show();
                            fetchUserLocation();
                        }
                    } catch (NullPointerException e) {
                        Toast.makeText(getApplicationContext(), "Something has gone wrong!", Toast.LENGTH_SHORT).show();
                        Log.e("currentLocation", e.toString());
                        e.printStackTrace();
                    }
                }
//                Intent intent = new Intent(Homepage.this, MapsActivity2.class);
//                startActivity(intent);
            }
        });
        emergencySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    showEmergencyPopup();
                }
            }
        });
    }

    private void showMedicalCasePopup() {
        View popupView = getLayoutInflater().inflate(R.layout.medical_case_popup, null);
        int width = 1000;
        int height = 1400;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(
                popupView, width, height, focusable);
        popupWindow.setElevation(10);
        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
        EditText medicalCaseInput = popupView.findViewById(R.id.medicalCaseInput);
        TextView requestMedicalCase = popupView.findViewById(R.id.requestMedicalCase);
        TextView cancelMedicalCase = popupView.findViewById(R.id.cancelMedicalCase);
        requestMedicalCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Save the pickup location in the database
                String medicalCase = medicalCaseInput.getText().toString();
                if (!TextUtils.isEmpty(medicalCase)) {
                    //Creating a new request and save in Firebase
                    RequestModel newRequest = new RequestModel();
                    newRequest.setRequestStatus(Status.REQUESTED);
                    newRequest.setEmergencyMode(false);
                    newRequest.setRequestDriver(requestedDriver);
                    newRequest.setRequestCase(medicalCase);
                    //Create request in Firebase node
                    boolean requested = requestViewModel.createRequest(newRequest);
                    if (requested) {
                        //Confirming the request by the driverID
                        driverService.confirmRequest(String.valueOf(requestedDriver.getDriverID()), new DriverService.ConfirmRequestCallback() {
                            @Override
                            public void onSuccess(String confirmationMessage) {
                                if (confirmationMessage.equals("Successful")) {
                                    Toast.makeText(getApplicationContext(), "Request confirmed", Toast.LENGTH_SHORT).show();
                                    //Requesting the hospital
                                    hospitalService.getNearestHospital(userLocationModel, new HospitalService.NearestHospitalCallback() {
                                        @Override
                                        public void onSuccess(HospitalModel hospitalModel) {
                                            Log.d("HOSPITAL", hospitalModel.toString());
                                            requestedHospital = new HospitalModel();
                                            requestedHospital.setHospitalName(hospitalModel.getHospitalName());
                                            requestedHospital.setHospitalID(hospitalModel.getHospitalID());
                                            requestedHospital.setHospitalLocationLat(hospitalModel.getHospitalLocationLat());
                                            requestedHospital.setHospitalLocationLong(hospitalModel.getHospitalLocationLong());
                                            requestedHospital.setNumberOfSeats(hospitalModel.getNumberOfSeats());
                                            Log.d("REQUESTEDHOSP", requestedHospital.toString());
                                            //Initialize the ETA model to make POST request on ETA Service
                                            ETAModel etaModel = new ETAModel();
                                            etaModel.setDriverLatitude(requestedDriver.getDriverLocationLat());
                                            etaModel.setDriverLongitude(requestedDriver.getDriverLocationLong());
                                            etaModel.setUserLatitude(userLocationModel.getLatitude());
                                            etaModel.setUserLongitude(userLocationModel.getLongitude());
                                            Log.d("ETAMODEL", etaModel.toString());
                                            //ETA POST Request
                                            etaService.getETA(etaModel, new ETAService.ETACallback() {
                                                @Override
                                                public void onSuccess(ETAResponse etaResponse) {
                                                    Toast.makeText(getApplicationContext(), "ETARESPONSE:" + etaResponse, Toast.LENGTH_SHORT).show();
                                                    Log.d("ETARESPONSE", etaResponse.getEstimatedTime());
                                                    popupWindow.dismiss();
                                                    Intent intent = new Intent(Homepage.this, MapsActivity2.class);
                                                    // Pass the variables as extras in the Intent
                                                    intent.putExtra("requestedDriver", requestedDriver);
                                                    intent.putExtra("requestedHospital", requestedHospital);
                                                    intent.putExtra("etaResponse", etaResponse);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                @Override
                                                public void onError(String errorMessage) {
                                                    Toast.makeText(getApplicationContext(), "ETARESPONSE:" + errorMessage, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        @Override
                                        public void onError(String errorMessage) {
                                            Toast.makeText(getApplicationContext(), "Couldn't request hospital", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "Request wasn't confirmed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onError(String errorMessage) {
                                Toast.makeText(getApplicationContext(), "Something has gone wrong" + errorMessage, Toast.LENGTH_SHORT).show();
                                Log.d("ConfirmRequest", errorMessage);
                            }
                        });
                        Toast.makeText(getApplicationContext(), "Created a request", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Couldn't create a request", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    medicalCaseInput.setError("Enter your medical case");
                    medicalCaseInput.requestFocus();
                }
            }
        });

        cancelMedicalCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });

        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void showEmergencyPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.emergency_popup, null);

        int width = 789;
        int height = 1032;
        boolean focusable = true;

        PopupWindow popupWindow = new PopupWindow(
                popupView, width, height, focusable);

        popupWindow.setElevation(10);
        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);

        TextView confirmEmergency = popupView.findViewById(R.id.confirmEmergencyRequest);
        TextView cancelRequest = popupView.findViewById(R.id.cancelEmergency);
        /**
         * Creating an Emergency request
         * */
        confirmEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new request and save in Firebase
                RequestModel newRequest = new RequestModel();
                newRequest.setRequestStatus(Status.REQUESTED);
                newRequest.setEmergencyMode(true);
                newRequest.setRequestDriver(requestedDriver);
                newRequest.setRequestCase("");
                //Create request in Firebase node
                boolean requested = requestViewModel.createRequest(newRequest);
                if (requested) {
                    //Confirming the request by the driverID
                    driverService.confirmRequest(String.valueOf(requestedDriver.getDriverID()), new DriverService.ConfirmRequestCallback() {
                        @Override
                        public void onSuccess(String confirmationMessage) {
                            if (confirmationMessage.equals("Successful")) {
                                Toast.makeText(getApplicationContext(), "Request confirmed", Toast.LENGTH_SHORT).show();
                                //Requesting the hospital
                                hospitalService.getNearestHospital(userLocationModel, new HospitalService.NearestHospitalCallback() {
                                    @Override
                                    public void onSuccess(HospitalModel hospitalModel) {
                                        Log.d("HOSPITAL", hospitalModel.toString());
                                        requestedHospital = new HospitalModel();
                                        requestedHospital.setHospitalName(hospitalModel.getHospitalName());
                                        requestedHospital.setHospitalID(hospitalModel.getHospitalID());
                                        requestedHospital.setHospitalLocationLat(hospitalModel.getHospitalLocationLat());
                                        requestedHospital.setHospitalLocationLong(hospitalModel.getHospitalLocationLong());
                                        requestedHospital.setNumberOfSeats(hospitalModel.getNumberOfSeats());
                                        Log.d("REQUESTEDHOSP", requestedHospital.toString());
                                        //Initialize the ETA model to make POST request on ETA Service
                                        ETAModel etaModel = new ETAModel();
                                        etaModel.setDriverLatitude(requestedDriver.getDriverLocationLat());
                                        etaModel.setDriverLongitude(requestedDriver.getDriverLocationLong());
                                        etaModel.setUserLatitude(userLocationModel.getLatitude());
                                        etaModel.setUserLongitude(userLocationModel.getLongitude());
                                        Log.d("ETAMODEL", etaModel.toString());
                                        //ETA POST Request
                                        etaService.getETA(etaModel, new ETAService.ETACallback() {
                                            @Override
                                            public void onSuccess(ETAResponse etaResponse) {
                                                Toast.makeText(getApplicationContext(), "ETARESPONSE:" + etaResponse, Toast.LENGTH_SHORT).show();
                                                Log.d("ETARESPONSE", etaResponse.getEstimatedTime());
                                                Intent intent = new Intent(Homepage.this, MapsActivity2.class);
                                                // Pass the variables as extras in the Intent
                                                intent.putExtra("requestedDriver", requestedDriver);
                                                intent.putExtra("requestedHospital", requestedHospital);
                                                intent.putExtra("etaResponse", etaResponse);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                                popupWindow.dismiss();
                                            }
                                            @Override
                                            public void onError(String errorMessage) {
                                                Toast.makeText(getApplicationContext(), "ETARESPONSE:" + errorMessage, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onError(String errorMessage) {
                                        Toast.makeText(getApplicationContext(), "Couldn't request hospital", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Request wasn't confirmed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(getApplicationContext(), "Something has gone wrong" + errorMessage, Toast.LENGTH_SHORT).show();
                            Log.d("ConfirmRequest", errorMessage);
                        }
                    });
                    Toast.makeText(getApplicationContext(), "Created a request", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Couldn't create a request", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                emergencySwitch.setChecked(false);
            }
        });

        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            locationService.stopLocationUpdates();
            unbindService(connection);
            bound = false;
        }
    }

    /**
     * Location service connection
     * and disconnection
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            LocationService.LocalBinder binder = (LocationService.LocalBinder) service;
            locationService = binder.getService();
            bound = true;

            if (ContextCompat.checkSelfPermission(Homepage.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationService.startLocationUpdates();
            }

            bound = true;
            fetchUserLocation();
        }


        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };

    /**
     * Checks for location permissions
     */
    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    new AlertDialog.Builder(this)
                            .setTitle("We need your location permission")
                            .setMessage("Please allow the location permission to enable location app functionalities")
                            .setPositiveButton("Okay", (dialogInterface, i) -> ActivityCompat.requestPermissions(Homepage.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION))
                            .create()
                            .show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
                }
            }
        }
    }

    /**
     * Starts the location service
     * if the permissions are acquired
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (bound) {
                    locationService.startLocationUpdates();
                }
            } else {
                Toast.makeText(this, "Sorry! Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Retrieving the Location
     * and save it in current location.
     */
    public void fetchUserLocation() {
        if (bound) {
            Location location = locationService.getCurrentLocation();
            if (location != null) {
                currentLocation = location;
                Log.d("Location", currentLocation.toString());
            }
        }
    }

}
