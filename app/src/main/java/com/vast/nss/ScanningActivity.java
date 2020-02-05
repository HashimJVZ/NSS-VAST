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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ScanningActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private String key;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =  firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        scannerView.setFormats(Collections.singletonList(BarcodeFormat.CODE_128));
        setContentView(scannerView);

        Bundle extras = getIntent().getExtras();
        key = Objects.requireNonNull(extras).getString("key");

        if (checkPermission()) {
            Toast.makeText(ScanningActivity.this, "access granted", Toast.LENGTH_LONG).show();
        } else {
            requestPermissions();
        }
    }

    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(ScanningActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionResult(int requestCode, String[] permission, int[] grantResults){
        if (requestCode == REQUEST_CAMERA) {
            boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (cameraAccepted) {
                Toast.makeText(ScanningActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ScanningActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();

                if (shouldShowRequestPermissionRationale(CAMERA)) {
                    displayAlertMessage("You need to allow access for both permissions", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(checkPermission()){
            if(scannerView == null){
                scannerView = new ZXingScannerView(this);
                scannerView.setFormats(Collections.singletonList(BarcodeFormat.CODE_128));
                setContentView(scannerView);
            }
            scannerView.setResultHandler(this);
            scannerView.startCamera();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(ScanningActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        Log.d("barcode_test", result.getBarcodeFormat().name());
        Toast.makeText(this,result.getBarcodeFormat().name() , Toast.LENGTH_SHORT).show();
        final String scanResult = result.getText();

        HashMap<String, Object> map = new HashMap<>();
        map.put("Id",scanResult);

        databaseReference.child("events").child(key).child("participants").updateChildren(map);



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(ScanningActivity.this);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();

    }
}
