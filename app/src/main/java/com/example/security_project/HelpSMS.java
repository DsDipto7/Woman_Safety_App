package com.example.security_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HelpSMS extends AppCompatActivity implements LocationListener{

    FirebaseAuth auth;
    String path,key;
    DatabaseReference mDatabase;

    Spinner myspinner;
    TextInputEditText message;

    ArrayList<Profile> contacts;
    ArrayList<String> names;
    Button sendBtn;
    String phone_number=null;
    LocationManager locationManager;
    String str_address="";
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_sms);
        myspinner=findViewById(R.id.myspinner);
        message=findViewById(R.id.text_msg);
        sendBtn=findViewById(R.id.sendbtn);
        contacts=new ArrayList<>();
        names=new ArrayList<>();
        setSpinnerItem();
        myspinner.setSelection(0);

        getLocation();

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (contacts != null && !contacts.isEmpty() && i < contacts.size()) {
                    phone_number = contacts.get(i).pnumber;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(HelpSMS.this, android.Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                    sendSMS();
                }else{
                    ActivityCompat.requestPermissions(HelpSMS.this,new String[]{android.Manifest.permission.SEND_SMS},101);
                }
            }
        });


    }

    private void sendSMS() {
        String msg=message.getText().toString();

        if (!msg.isEmpty() && phone_number != null) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone_number, null, msg+"\nCurrent Location: "+str_address, null, null);
            Toast.makeText(HelpSMS.this, "Uploaded", Toast.LENGTH_SHORT).show();

        } else if (phone_number == null) {
            Toast.makeText(HelpSMS.this, "Select a contact from the spinner", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(HelpSMS.this, "Enter Message", Toast.LENGTH_SHORT).show();
        }

    }


    private void setSpinnerItem() {

        auth=FirebaseAuth.getInstance();
        key=auth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference(key+"/contacts");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot val: snapshot.getChildren()){
                        Profile contact=val.getValue(Profile.class);
                        if(contact!=null){
                            contacts.add(contact);
                            names.add(contact.name);

                        }
                    }

                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(HelpSMS.this, android.R.layout.simple_spinner_item,names);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    myspinner.setAdapter(arrayAdapter);
                }else{
                    System.out.println("Data does not exist or some error");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==101 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            sendSMS();
        }else{
            Toast.makeText(HelpSMS.this,"Permission is denied",Toast.LENGTH_SHORT).show();
        }

    }

    //location
    private void getLocation() {

        if (ContextCompat.checkSelfPermission(HelpSMS.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HelpSMS.this,new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            if (locationManager != null) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5, this);
            } else {
                Toast.makeText(HelpSMS.this, "Location manager not available", Toast.LENGTH_SHORT).show();
            }
            }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(HelpSMS.this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(HelpSMS.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            Toast.makeText(HelpSMS.this, address, Toast.LENGTH_SHORT).show();
            if(flag==0) {
                flag=1;
                str_address += address;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}