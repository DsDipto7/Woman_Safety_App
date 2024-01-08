package com.example.security_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddContact extends AppCompatActivity {

    TextInputEditText edit_name,phone_number;
    Button savebtn;
    FirebaseAuth auth;
    String path,key;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        edit_name=findViewById(R.id.name);
        phone_number=findViewById(R.id.phone_num);
        savebtn=findViewById(R.id.savebtn);
        auth=FirebaseAuth.getInstance();

        key=auth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference(key+"/contacts");
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               uploadToFirebase();
                Toast.makeText(AddContact.this,"Uploaded",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void uploadToFirebase() {

        String pnumber = phone_number.getText().toString();
        String name = edit_name.getText().toString();
        path=mDatabase.push().getKey();
        if(!pnumber.isEmpty() && (pnumber.length()==11) && !name.isEmpty()){
            Profile user = new Profile(pnumber,name);

            mDatabase.child(path).setValue(user);
            edit_name.setText("");
            phone_number.setText("");
        }else{
            Toast.makeText(AddContact.this,"Please enter the info correctly",Toast.LENGTH_SHORT).show();
        }

    }
}