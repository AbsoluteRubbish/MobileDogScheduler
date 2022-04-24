package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.schedulingApp.Adapters.AdapterServices;
import com.example.schedulingApp.Entities.EntityServices;
import com.example.deliveryapp.R;
import com.example.schedulingApp.utils.Repository;

import java.util.List;

public class ServicesActivity extends AppCompatActivity {
    Repository repository;
    RecyclerView recyclerView;
    AdapterServices adapterServices;
    List<EntityServices> servicesList;
    int serviceID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        repository = new Repository(getApplication());

        servicesList = repository.getAllServices();
        adapterServices  = new AdapterServices(this, repository);
        serviceID = getIntent().getIntExtra("serviceID", -1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        serviceRecyclerViews();
    }

    private void serviceRecyclerViews(){
        recyclerView = findViewById(R.id.orderRecyclerView);
        recyclerView.setAdapter(adapterServices);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                serviceRecyclerViews();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToAddOrder(View view){
        Intent intent = new Intent(ServicesActivity.this, AddServiceActivity.class);
        startActivity(intent);
    }

}