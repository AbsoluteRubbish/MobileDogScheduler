package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.deliveryapp.R;

public class MainActivityStaff extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_staff);

        Button staffClientBtn = findViewById(R.id.ownersButton);
        Button staffDogBtn = findViewById(R.id.dogsButton);
        Button staffServiceBtn = findViewById(R.id.serviceButton);


        staffClientBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityStaff.this, OwnersActivity.class);
            startActivity(intent);
        });
        staffDogBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityStaff.this, DogActivity.class);
            startActivity(intent);
        });
        staffServiceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityStaff.this, ServicesActivity.class);
            startActivity(intent);
        });
    }
}
