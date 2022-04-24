package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
;
import com.example.schedulingApp.Adapters.AdapterOwners;
import com.example.deliveryapp.R;
import com.example.schedulingApp.Entities.EntityOwners;
import com.example.schedulingApp.utils.Repository;

import java.util.ArrayList;
import java.util.List;

public class OwnersActivity extends AppCompatActivity {
    Repository repository;
    RecyclerView recyclerView;

    AdapterOwners adapterOwners;
    int ownerID;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners);
        repository = new Repository(getApplication());

        List<EntityOwners> ownersList = repository.getAllOwners();
        adapterOwners = new AdapterOwners(this, ownersList);
        ownerID = getIntent().getIntExtra("ownerID", -1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.clientRecyclerView);
        recyclerView.setAdapter(adapterOwners);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterOwners.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshList:
                recyclerView = findViewById(R.id.clientRecyclerView);
                repository = new Repository(getApplication());
                List<EntityOwners> ownersList = repository.getAllOwners();
                adapterOwners = new AdapterOwners(this, ownersList);

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                recyclerView.setAdapter(adapterOwners);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToAddClient(View view){
        Intent intent = new Intent(OwnersActivity.this, AddOwnerActivity.class);
        startActivity(intent);
    }

}