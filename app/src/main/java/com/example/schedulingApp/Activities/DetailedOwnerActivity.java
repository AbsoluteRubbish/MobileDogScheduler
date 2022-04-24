package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Adapters.AdapterDogs;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityOwners;
import com.example.schedulingApp.utils.Repository;

import java.util.ArrayList;
import java.util.List;

public class DetailedOwnerActivity extends AppCompatActivity {
    Repository repository;

    int ownerID;
    EditText ownerName;
    EditText ownerAddress;
    EditText ownerPhone;
    EditText ownerEmail;

    boolean isValid;

    int numDogs;
    EntityOwners currentOwners;
    RecyclerView dogRecyclerView;
    AdapterDogs adapterDogs;
    List<EntityDogs> dogsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_owner);
        repository = new Repository(getApplication());
        dogsList = repository.getAllDogs();
        adapterDogs = new AdapterDogs(this, dogsList);

        ownerName = findViewById(R.id.edit_detailed_ownerName);
        ownerAddress = findViewById(R.id.edit_detailed_ownerAddress);
        ownerPhone = findViewById(R.id.edit_detailed_ownerPhone);
        ownerEmail = findViewById(R.id.edit_detailed_ownerEmail);

        ownerID = getIntent().getIntExtra("ownerID", -1);
        ownerName.setText(getIntent().getStringExtra("ownerName"));
        ownerAddress.setText(getIntent().getStringExtra("ownerAddress"));
        ownerPhone.setText(getIntent().getStringExtra("ownerPhone"));
        ownerEmail.setText(getIntent().getStringExtra("ownerEmail"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerViewDogsUnderOwners();
    }

    //recycle view for dogs under owners
    public void recyclerViewDogsUnderOwners(){

        dogRecyclerView = findViewById(R.id.dogsUnderOwnersRecyclerView);
        adapterDogs = new AdapterDogs(this, dogsList);
        dogRecyclerView.setAdapter(adapterDogs);
        dogRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<EntityDogs> filteredDogs = new ArrayList<>();
        for (EntityDogs d : repository.getAllDogs()){
            if(d.getOwnerID() == ownerID) filteredDogs.add(d);
        }
        adapterDogs.setDog(filteredDogs);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    public void toDogButton(View view){
        Intent intent = new Intent(DetailedOwnerActivity.this, AddDogActivity.class);
        Bundle b = new Bundle();
        b.putInt("ownerID", ownerID);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void isValid(){
        EntityOwners entityOwners;
        if (ownerName.getText().toString().trim().isEmpty() || ownerAddress.getText().toString().trim().isEmpty()
                || ownerPhone.getText().toString().trim().isEmpty() | ownerEmail.getText().toString().trim().isEmpty() ){
            Toast.makeText(DetailedOwnerActivity.this, "Make sure all fields are filled.Owner not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }else{
            entityOwners = new EntityOwners(ownerID, ownerName.getText().toString(), ownerAddress.getText().toString(), ownerPhone.getText().toString(), ownerEmail.getText().toString());
            repository.update(entityOwners);
            Toast.makeText(DetailedOwnerActivity.this, "Owner updated, please refresh page.", Toast.LENGTH_LONG).show();
            isValid = true;
        }
    }

    public void canDelete(){
        for (EntityOwners owners : repository.getAllOwners()){
            if(owners.getOwnerID() == ownerID) currentOwners = owners;
        }
        numDogs = 0;
        for (EntityDogs dogs: repository.getAllDogs()){
            if(dogs.getOwnerID() == ownerID) ++numDogs;
        }
        if(numDogs == 0){
            repository.delete(currentOwners);
            Toast.makeText(DetailedOwnerActivity.this, currentOwners.getOwnerName() + " was deleted. Please refresh page.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(DetailedOwnerActivity.this, "Can not delete owners with dogs. Delete dogs first.", Toast.LENGTH_LONG).show();
        }
    }

    public void saveOwnerButton(View view){
        isValid();
        finish();
    }
    public void deleteOwnerButton(View view){
        canDelete();
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveItem:
                isValid();
                finish();
                return true;
            case R.id.deleteItem:
                canDelete();
                finish();
                return true;
            case R.id.refreshList:
               recyclerViewDogsUnderOwners();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}