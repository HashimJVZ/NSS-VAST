package com.vast.nss;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ScanningActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(ScanningActivity.this, "access granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermissions();
            }
        }
    }

    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(ScanningActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionResult(int requestCode, String permission[], int grantResults[]){
        switch(requestCode) {
            case REQUEST_CAMERA:
                if(grantResults.length >= 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted){
                        Toast.makeText(ScanningActivity.this,"Permission Granted", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ScanningActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                        }
                    }
                }
        }
    }

    displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(ScanningActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton.("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {

    }
}
