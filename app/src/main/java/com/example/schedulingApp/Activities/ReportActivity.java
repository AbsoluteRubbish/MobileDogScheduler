package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.deliveryapp.R;
import com.example.schedulingApp.utils.Repository;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button monthBtn = findViewById(R.id.serviceByMonthButton);
        Button employeeBtn = findViewById(R.id.servicesByEmployeeButton);

        monthBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ReportActivity.this, ReportByMonthActivity.class);
            startActivity(intent);
        });
        employeeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ReportActivity.this, ReportByEmployeeActivity.class);
            startActivity(intent);
        });
    }
}