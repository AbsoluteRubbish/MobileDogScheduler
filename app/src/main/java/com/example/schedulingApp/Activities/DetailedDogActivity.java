package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Adapters.AdapterServices;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityOwners;
import com.example.schedulingApp.Entities.EntityServices;
import com.example.schedulingApp.ViewModel.DogAggression;
import com.example.schedulingApp.ViewModel.DogWeight;
import com.example.schedulingApp.ViewModel.OwnerViewModel;
import com.example.schedulingApp.utils.Repository;

import java.util.ArrayList;
import java.util.List;

public class DetailedDogActivity extends AppCompatActivity {
    Repository repository;

    int dogID;
    EditText dogName;
    EditText dogBreed;

    Spinner editDogWeight;
    private ArrayAdapter<DogWeight> weightArrayAdapter;
    Spinner editDogAggression;
    private ArrayAdapter<DogAggression> aggressionArrayAdapter;
    Spinner dogOwner;
    private ArrayAdapter<OwnerViewModel> ownerAdapter;

    RecyclerView serviceRV;
    AdapterServices adapterServices;

    private int ownerID;
    boolean isValid;

    int numServices;
    EntityDogs currentDogs;
    List<EntityServices> servicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_dog);
        repository = new Repository(getApplication());
        servicesList = repository.getAllServices();
        adapterServices = new AdapterServices(this, repository);

        dogName = findViewById(R.id.edit_detailed_dogName);
        dogBreed = findViewById(R.id.edit_detailed_dogBreed);
        editDogWeight = findViewById(R.id.edit_detailed_dogWeight);
        editDogAggression = findViewById(R.id.edit_detailed_DogAggressive);
        dogOwner = findViewById(R.id.edit_detailed_DogOwner);

        Bundle b = getIntent().getExtras();

        PopulateAllSpinners();

        dogID = getIntent().getIntExtra("dogID", -1);
        dogName.setText(getIntent().getStringExtra("dogName"));
        dogBreed.setText(getIntent().getStringExtra("dogBreed"));

        if(getIntent().getStringExtra("dogAggression") !=null){
            setAggStatus(DogAggression.valueOf(getIntent().getStringExtra("dogAggression")));
        }

        if(getIntent().getStringExtra("dogWeight") !=null){
            setWeightStatus(DogWeight.valueOf(getIntent().getStringExtra("dogWeight")));
        }

        ownerID = getIntent().getIntExtra("ownerID", -1);
        dogOwner.setSelection(indexInOwnerSpinner(ownerID));
        if(ownerID != -1) {
            b.getInt("ownerID");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerViewServicesUnderDogs();

    }
    public void recyclerViewServicesUnderDogs(){
        serviceRV = findViewById(R.id.servicesUnderDogRecyclerView);
        adapterServices = new AdapterServices(this, repository);
        serviceRV.setAdapter(adapterServices);
        serviceRV.setLayoutManager(new LinearLayoutManager(this));

        List<EntityServices> filteredServices = new ArrayList<>();
        for (EntityServices s : repository.getAllServices()){
            if(s.getDogID() == dogID) filteredServices.add(s);
        }
        adapterServices.setServices(filteredServices);
    }

    private void PopulateAllSpinners(){
        //weight
        weightArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DogWeight.values());
        editDogWeight.setAdapter(weightArrayAdapter);
        //aggression
        aggressionArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DogAggression.values());
        editDogAggression.setAdapter(aggressionArrayAdapter);
        //owners
        List<OwnerViewModel> spinnerOwnerName = getOwnerViewModel();
        ownerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerOwnerName);
        ownerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dogOwner.setAdapter(ownerAdapter);
    }
    private int getIndexInSpinner(Spinner spinner, String s){
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }

    private int indexInOwnerSpinner(int ownerID){
        for(int i=0; i<dogOwner.getCount(); i++){
            OwnerViewModel item = (OwnerViewModel) dogOwner.getItemAtPosition(i);
            if(item.id == ownerID){
                return i;
            }
        }
        return -1;
    }

    //set spinners for
    private void setAggStatus(DogAggression value){
        editDogAggression.setSelection(getIndexInSpinner(editDogAggression, value.toString()));
    }
    private void setWeightStatus(DogWeight value){
        editDogWeight.setSelection(getIndexInSpinner(editDogWeight, value.toString()));
    }

// get info from spinners

    private DogWeight SpinnerWeightValue() {
        return (DogWeight) editDogWeight.getSelectedItem();
    }
    private DogAggression SpinnerAggressionValue() {
        return (DogAggression) editDogAggression.getSelectedItem();
    }

    private int SpinnerOwnerIDValue(){
        OwnerViewModel item = (OwnerViewModel) dogOwner.getSelectedItem();
        return item.id;
    }

    private List<OwnerViewModel> getOwnerViewModel(){
        List<EntityOwners> ownerName = repository.getAllOwners();
        List<OwnerViewModel> o = new ArrayList<>();
        for (int i=0; i<ownerName.size(); i++){
            o.add(new OwnerViewModel(ownerName.get(i)));
        }
        return o;
    }

    public void toServicesButton(View view){
        Intent intent = new Intent(DetailedDogActivity.this, AddServiceActivity.class);
        Bundle b = new Bundle();
        b.putInt("dogID", dogID);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void isValid(){
        EntityDogs entityDogs;
        if (dogName.getText().toString().trim().isEmpty() || dogBreed.getText().toString().trim().isEmpty()){
            Toast.makeText(DetailedDogActivity.this, "Make sure all fields are filled. Dog not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }else{
            entityDogs = new EntityDogs(dogID, dogName.getText().toString(), dogBreed.getText().toString(), SpinnerAggressionValue(), SpinnerWeightValue(), SpinnerOwnerIDValue());
            repository.update(entityDogs);
            Toast.makeText(DetailedDogActivity.this, "Dog updated, please refresh page.", Toast.LENGTH_LONG).show();
            isValid = true;
        }
    }

    public void canDelete(){
        for (EntityDogs dogs : repository.getAllDogs()){
            if(dogs.getDogID() == dogID) currentDogs = dogs;
        }
        numServices = 0;
        for(EntityServices services: repository.getAllServices()){
            if(services.getEmployeeID() == dogID) ++numServices;
        }
        if(numServices == 0) {
            repository.delete(currentDogs);
            Toast.makeText(DetailedDogActivity.this, currentDogs.getDogName() + " was deleted. Please refresh page.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(DetailedDogActivity.this, currentDogs.getDogName() + " can not be deleted. Delete services first.", Toast.LENGTH_LONG).show();
        }
    }

    public void saveDetailedDogButton(View view){
        isValid();
        finish();
    }

    public void deleteDogButton(View view){
        canDelete();
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
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
                recyclerViewServicesUnderDogs();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}