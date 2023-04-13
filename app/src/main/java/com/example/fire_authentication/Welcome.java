package com.example.fire_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class Welcome extends AppCompatActivity {
    TextView t1,t2;
    FirebaseDatabase fb;
    FirebaseAuth fauth;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        fauth = FirebaseAuth.getInstance();
        userID = fauth.getCurrentUser().getUid();
        fb = FirebaseDatabase.getInstance();

        fb.getReference("users").child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Welcome.this, "data exists", Toast.LENGTH_SHORT).show();
                    DataSnapshot dataSnapshot = task.getResult();
                    String namee = String.valueOf(dataSnapshot.child("name").getValue());
                    t1.setText(namee);
                    String emaill = String.valueOf(dataSnapshot.child("email").getValue());
                    t2.setText(emaill);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Welcome.this, "data not exists", Toast.LENGTH_SHORT).show();
            }
        });

    }
}