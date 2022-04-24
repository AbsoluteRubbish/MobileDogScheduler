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
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Adapters.AdapterMonth;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityServices;
import com.example.schedulingApp.ViewModel.ProductAddOns;
import com.example.schedulingApp.utils.Repository;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportByMonthActivity extends AppCompatActivity {
    Repository repository;
    RecyclerView recyclerView;
    List<EntityServices> servicesList;
    AdapterMonth adapterMonth;
    List<EntityDogs> dogsList;

    Spinner monthSpinner;
    private ArrayAdapter<Month> monthArrayAdapter;

    TextView timestamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_by_month);
        repository = new Repository(getApplication());

        servicesList = repository.getAllServices();
        dogsList = repository.getAllDogs();
        adapterMonth = new AdapterMonth(this,repository);

        timestamp = findViewById(R.id.text_view_timeStamp);
        Date currentTime = Calendar.getInstance().getTime();
        timestamp.setText(currentTime.toString());

        monthSpinner = findViewById(R.id.month_Spinner);
        //populate spinner
        monthArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Month.values());
        monthSpinner.setAdapter(monthArrayAdapter);
        //set month to current month
        int indexOfMonth = Calendar.getInstance().get(Calendar.MONTH);
        monthSpinner.setSelection(indexOfMonth);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterMonth.getFilter().filterByMonth(Month.values()[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.monthRecyclerView);
        recyclerView.setAdapter(adapterMonth);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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