package com.vast.nss;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Collections;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ScanningActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private String dbEventKey;
    private String enrollmentNumber;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        scannerView.setFormats(Collections.singletonList(BarcodeFormat.CODE_128));
        setContentView(scannerView);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        dbEventKey = extras.getString("dbEventKey", "0000");
        Log.d("dbEventKey", "dbEventKey= " + dbEventKey);
        if (checkPermission()) {
            Toast.makeText(ScanningActivity.this, "access granted", Toast.LENGTH_LONG).show();
        } else {
            requestPermissions();
        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(ScanningActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionResult(int requestCode, String[] permission, int[] grantResults) {
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
    public void onResume() {
        super.onResume();
        if (checkPermission()) {
            if (scannerView == null) {
                scannerView = new ZXingScannerView(this);
                scannerView.setFormats(Collections.singletonList(BarcodeFormat.CODE_128));
                setContentView(scannerView);
            }
            scannerView.setResultHandler(this);
            scannerView.startCamera();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
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
        Toast.makeText(this, result.getBarcodeFormat().name(), Toast.LENGTH_SHORT).show();
        final String scanResult = result.getText();

//        HashMap<String, Object> map = new HashMap<>();
//        map.put("Id", scanResult);

        databaseReference.child("participants").child(dbEventKey).child(scanResult).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(ScanningActivity.this, "Already Marked", Toast.LENGTH_SHORT).show();
                } else {
                    getEnrollmentNumber();
                    databaseReference.child("participants").child(dbEventKey).child(scanResult).setValue(enrollmentNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        databaseReference.child("events").child(dbEvent_Key).child("participants").updateChildren(map);
//        databaseReference.child("participants").child(dbEvent_Key).push().updateChildren(map);

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

    private void getEnrollmentNumber() {
        enrollmentNumber = "do it bitch";
    }
}
