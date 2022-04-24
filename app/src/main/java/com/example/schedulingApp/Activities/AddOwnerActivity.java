package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Entities.EntityOwners;
import com.example.schedulingApp.utils.Repository;

public class AddOwnerActivity extends AppCompatActivity {

    EditText addName;
    EditText addAddress;
    EditText addPhone;
    EditText addEmail;

    int ownerID;

    boolean isValid;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_owner);
        repository = new Repository(getApplication());

        addName = findViewById(R.id.add_ownerName);
        addAddress = findViewById(R.id.add_ownerAddress);
        addPhone = findViewById(R.id.add_ownerPhone);
        addEmail = findViewById(R.id.add_ownerEmail);

        ownerID = getIntent().getIntExtra("ownerID", -1);

        addName.setText(getIntent().getStringExtra("ownerName"));
        addAddress.setText(getIntent().getStringExtra("ownerAddress"));
        addPhone.setText(getIntent().getStringExtra("ownerPhone"));
        addEmail.setText(getIntent().getStringExtra("ownerEmail"));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    public void isValid(){
        EntityOwners entityOwners;
        if (addName.getText().toString().trim().isEmpty() || addAddress.getText().toString().trim().isEmpty()
                || addPhone.getText().toString().trim().isEmpty() | addEmail.getText().toString().trim().isEmpty() ){
            Toast.makeText(AddOwnerActivity.this, "Make sure all fields are filled.Owner not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        else if(ownerID == -1){
            int newID = repository.getAllOwners().get(repository.getAllOwners().size()-1).getOwnerID()+1;
            entityOwners = new EntityOwners(newID, addName.getText().toString(), addAddress.getText().toString(), addPhone.getText().toString(), addEmail.getText().toString());
            repository.insert(entityOwners);
            isValid = true;
        }else{
            entityOwners = new EntityOwners(ownerID, addName.getText().toString(), addAddress.getText().toString(), addPhone.getText().toString(), addEmail.getText().toString());
            repository.update(entityOwners);
            isValid = true;
        }
    }

    public void saveOwnerButton(View view){
        isValid();
        Toast.makeText(AddOwnerActivity.this, "Owner was added. Please refresh page.", Toast.LENGTH_LONG).show();
        finish();
    }



    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveItem:
                isValid();

                Toast.makeText(AddOwnerActivity.this, "Owner was added. Please refresh page.", Toast.LENGTH_LONG).show();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}