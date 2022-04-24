package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.schedulingApp.Adapters.AdapterEmployees;
import com.example.schedulingApp.Entities.EntityEmployees;
import com.example.deliveryapp.R;
import com.example.schedulingApp.utils.Repository;

import java.util.List;

public class EmployeeActivity extends AppCompatActivity {
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        employeeRecyclerViews();
    }
    private void employeeRecyclerViews(){
        RecyclerView recyclerView = findViewById(R.id.employeeRecyclerView);
        repository = new Repository(getApplication());
        List<EntityEmployees> employeesList = repository.getAllEmployees();
        final AdapterEmployees adapterEmployees = new AdapterEmployees(this);
        recyclerView.setAdapter(adapterEmployees);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterEmployees.setEmployees(employeesList);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshList:
                employeeRecyclerViews();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToAddEmployee(View view){
        Intent intent = new Intent(EmployeeActivity.this, AddEmployeeActivity.class);
        startActivity(intent);
    }

}