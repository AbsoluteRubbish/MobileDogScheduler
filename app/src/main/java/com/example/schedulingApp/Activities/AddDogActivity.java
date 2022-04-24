package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityOwners;
import com.example.schedulingApp.ViewModel.DogAggression;
import com.example.schedulingApp.ViewModel.DogWeight;
import com.example.schedulingApp.ViewModel.OwnerViewModel;
import com.example.schedulingApp.utils.Repository;

import java.util.ArrayList;
import java.util.List;

public class AddDogActivity extends AppCompatActivity {

    Repository repository;

    int dogID;
    EditText dogName;
    EditText dogBreed;

    Spinner dogWeight;
    private ArrayAdapter<DogWeight> weightArrayAdapter;
    Spinner dogAggression;
    private ArrayAdapter<DogAggression> aggressionArrayAdapter;
    Spinner dogOwner;
    private ArrayAdapter<OwnerViewModel> ownerAdapter;

    private int ownerID;
    boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);
        repository = new Repository(getApplication());

        dogName = findViewById(R.id.add_dogName);
        dogBreed = findViewById(R.id.add_dogBreed);
        dogWeight = findViewById(R.id.add_dogWeight);
        dogAggression = findViewById(R.id.add_DogAggressive);
        dogOwner = findViewById(R.id.add_DogOwner);

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
    }

    private void PopulateAllSpinners(){
        //weight
        weightArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DogWeight.values());
        dogWeight.setAdapter(weightArrayAdapter);
        //aggression
        aggressionArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DogAggression.values());
        dogAggression.setAdapter(aggressionArrayAdapter);
        //owners
        List<OwnerViewModel> spinnerOwnerName =  getOwnerViewModel();
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
        dogAggression.setSelection(getIndexInSpinner(dogAggression, value.toString()));
    }
    private void setWeightStatus(DogWeight value){
        dogWeight.setSelection(getIndexInSpinner(dogWeight, value.toString()));
    }

// get info from spinners

    private DogWeight SpinnerWeightValue() {
        return (DogWeight) dogWeight.getSelectedItem();
    }
    private DogAggression SpinnerAggressionValue() {
        return (DogAggression) dogAggression.getSelectedItem();
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


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    public void isValid(){
        EntityDogs entityDogs;
        if (dogName.getText().toString().trim().isEmpty() || dogBreed.getText().toString().trim().isEmpty()){
            Toast.makeText(AddDogActivity.this, "Make sure all fields are filled. Dog not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        else if(dogID == -1){
            int newID = repository.getAllDogs().get(repository.getAllDogs().size()-1).getDogID()+1;
            entityDogs = new EntityDogs(newID, dogName.getText().toString(), dogBreed.getText().toString(), SpinnerAggressionValue(), SpinnerWeightValue(), SpinnerOwnerIDValue());
            repository.insert(entityDogs);
            isValid = true;
        }else{
            entityDogs = new EntityDogs(dogID, dogName.getText().toString(), dogBreed.getText().toString(), SpinnerAggressionValue(), SpinnerWeightValue(), SpinnerOwnerIDValue());
            repository.update(entityDogs);
            isValid = true;
        }
    }

    public void saveDogButton(View view){
        isValid();
        finish();
        Toast.makeText(AddDogActivity.this, "Dog added. Refresh page.", Toast.LENGTH_LONG).show();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveItem:
                isValid();
                finish();
                Toast.makeText(AddDogActivity.this, "Dog added. Refresh page.", Toast.LENGTH_LONG).show();
                return true;
        }
        return  super.onOptionsItemSelected(item);
    }
}