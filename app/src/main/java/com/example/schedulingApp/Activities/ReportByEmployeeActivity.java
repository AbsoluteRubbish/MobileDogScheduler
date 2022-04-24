package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Adapters.AdapterEmployeeReport;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityEmployees;
import com.example.schedulingApp.Entities.EntityServices;
import com.example.schedulingApp.ViewModel.DogViewModel;
import com.example.schedulingApp.ViewModel.EmployeeViewModel;
import com.example.schedulingApp.utils.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportByEmployeeActivity extends AppCompatActivity {
    Repository repository;
    RecyclerView recyclerView;
    List<EntityEmployees> employeesList;
    AdapterEmployeeReport adapterEmployeeReport;
    List<EntityServices> servicesList;

    Spinner employeeSpinner;
    private ArrayAdapter<EmployeeViewModel> employeesArrayAdapter;

    TextView timeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_by_employee);
        repository = new Repository(getApplication());

        servicesList = repository.getAllServices();
        employeesList = repository.getAllEmployees();
        adapterEmployeeReport = new AdapterEmployeeReport(this, repository);

        timeStamp = findViewById(R.id.text_view_timeStampE);
        Date currentTime = Calendar.getInstance().getTime();
        timeStamp.setText(currentTime.toString());

        employeeSpinner = findViewById(R.id.employee_Spinner);
        //populate spinner
        List<EmployeeViewModel> spinnerEmployeeName = getEmployeeViewModel();
        employeesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerEmployeeName);
        employeesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(employeesArrayAdapter);

        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                EmployeeViewModel m = spinnerEmployeeName.get(i);
                adapterEmployeeReport.getFilter().filterByEmployeeID(m.id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterEmployeeReport.getFilter().filterByEmployeeID(null);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.employeeRecyclerView);
        recyclerView.setAdapter(adapterEmployeeReport);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<EmployeeViewModel> getEmployeeViewModel(){
        List<EntityEmployees> employeeName = repository.getAllEmployees();
        List<EmployeeViewModel> e = new ArrayList<>();
        for (int i=0; i<employeeName.size(); i++){
            e.add(new EmployeeViewModel(employeeName.get(i)));
        }
        return e;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share, menu);
        return  true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}