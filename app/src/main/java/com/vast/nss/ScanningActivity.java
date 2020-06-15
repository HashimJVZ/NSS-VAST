package com.vast.nss;

import android.content.DialogInterface;
import android.content.SharedPreferences;
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
    long hours;
    String category;
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
        category = extras.getString("category", "default");
        hours = extras.getLong("hours", 0);
        if (checkPermission()) {
            Toast.makeText(ScanningActivity.this, "Camera Access Granted", Toast.LENGTH_SHORT).show();
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

//    public void onRequestPermissionResult(int requestCode, String[] permission, int[] grantResults) {
//        if (requestCode == REQUEST_CAMERA) {
//            boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//            if (cameraAccepted) {
//                Toast.makeText(ScanningActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(ScanningActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
//
//                if (shouldShowRequestPermissionRationale(CAMERA)) {
//                    displayAlertMessage("You need to allow access for both permissions", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
//                        }
//                    });
//                }
//            }
//        }
//    }

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

//    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
//        new AlertDialog.Builder(ScanningActivity.this)
//                .setMessage(message)
//                .setPositiveButton("OK", listener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }

    @Override
    public void handleResult(Result result) {
//        Log.d("barcode_test", result.getBarcodeFormat().name());
        final String scanResult = result.getText();

        databaseReference.child("participants").child(dbEventKey).child(scanResult).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(ScanningActivity.this, "Already Marked!", Toast.LENGTH_SHORT).show();
                } else {
                    getEnrollmentNumber(scanResult);
                    Toast.makeText(ScanningActivity.this, "Added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                scannerView.resumeCameraPreview(ScanningActivity.this);
                markAttendance(scanResult);
                finish();
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void markAttendance(String scanResult) {
        Log.d("hashim", "markAttendance:\n db scRes env " + dbEventKey + " " + scanResult + " " + enrollmentNumber);
        databaseReference.child("participants").child(dbEventKey).child(scanResult).setValue(enrollmentNumber);

        databaseReference.child("profile").child(getUser()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                switch (category) {
                    case "Orientation":
                        databaseReference.child("profile").child(getUser()).child("orientationHour").setValue((Long) dataSnapshot.child("orientationHour").getValue() + hours);
                        break;
                    case "Campus":
                        databaseReference.child("profile").child(getUser()).child("campusHour").setValue((Long) dataSnapshot.child("campusHour").getValue() + hours);
                        break;
                    case "Community":
                        databaseReference.child("profile").child(getUser()).child("communityHour").setValue((Long) dataSnapshot.child("communityHour").getValue() + hours);
                        break;
                    case "Camp":
                        databaseReference.child("profile").child(getUser()).child("campHour").setValue((Long) dataSnapshot.child("campHour").getValue() + hours);
                        break;
                    default:
                        Toast.makeText(ScanningActivity.this, "Error in Marking", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getUser() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("SharedPref", MODE_PRIVATE);
        return sharedPreferences.getString("userName", null);
    }

    private void getEnrollmentNumber(String scanResult) {
        databaseReference.child("profile").orderByChild("collegeId").equalTo(scanResult).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    enrollmentNumber = childDataSnapshot.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
