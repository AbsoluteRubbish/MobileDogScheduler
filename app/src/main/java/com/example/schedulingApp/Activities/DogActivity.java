package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Adapters.AdapterDogs;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.utils.Repository;
import com.google.ar.core.Config;

import java.util.List;
public class DogActivity extends AppCompatActivity {
    Repository repository;
    RecyclerView recyclerView;

    List<EntityDogs> dogsList;
    AdapterDogs adapterDogs;
    int dogID;
    SearchView searchView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);
        repository = new Repository(getApplication());

        dogsList = repository.getAllDogs();
        adapterDogs = new AdapterDogs(this, dogsList);
        dogID = getIntent().getIntExtra("dogID", -1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dogRecyclerView();
    }

    public void dogRecyclerView(){
        recyclerView = findViewById(R.id.dogRecyclerView);
        recyclerView.setAdapter(adapterDogs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterDogs.getFilter().filter(newText);
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
                dogRecyclerView();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToAddDog(View view){
        Intent intent = new Intent(DogActivity.this, AddDogActivity.class);
        startActivity(intent);
    }

}