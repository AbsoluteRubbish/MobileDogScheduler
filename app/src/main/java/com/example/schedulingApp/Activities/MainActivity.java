package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityEmployees;
import com.example.schedulingApp.Entities.EntityOwners;
import com.example.schedulingApp.Entities.EntityServices;
import com.example.schedulingApp.ViewModel.DogAggression;
import com.example.schedulingApp.ViewModel.DogWeight;
import com.example.schedulingApp.ViewModel.EmployeePosition;
import com.example.schedulingApp.ViewModel.ProductAddOns;
import com.example.schedulingApp.ViewModel.Products;
import com.example.schedulingApp.utils.Repository;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Repository repository = new Repository(getApplication());

        Button staffBtn = findViewById(R.id.employeeButton);
        Button clientBtn = findViewById(R.id.ownersButton);
        Button dogBtn = findViewById(R.id.dogsButton);
        Button serviceBtn = findViewById(R.id.serviceButton);
        Button reportBtn = findViewById(R.id.reportButton);
        Button loginBtn = findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CredentialActivity.class);
            startActivity(intent);
        });

        staffBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
            startActivity(intent);
        });

        clientBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OwnersActivity.class);
            startActivity(intent);
        });
        dogBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DogActivity.class);
            startActivity(intent);
        });
        serviceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ServicesActivity.class);
            startActivity(intent);
        });
        reportBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
            startActivity(intent);
        });
    }
}