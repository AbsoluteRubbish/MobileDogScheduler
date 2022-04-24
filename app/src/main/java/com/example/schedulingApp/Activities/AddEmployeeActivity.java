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
import com.example.schedulingApp.Entities.EntityEmployees;
import com.example.schedulingApp.Entities.EntityOwners;
import com.example.schedulingApp.ViewModel.EmployeePosition;
import com.example.schedulingApp.utils.Repository;

public class AddEmployeeActivity extends AppCompatActivity {
    Repository repository;

    int employeeID;
    EditText addName;
    EditText addPhone;
    EditText addEmail;

    Spinner employeePosition;
    private ArrayAdapter<EmployeePosition> positionArrayAdapter;

    boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        repository = new Repository(getApplication());

        addName = findViewById(R.id.add_employeeName);
        addPhone = findViewById(R.id.add_employeePhone);
        addEmail = findViewById(R.id.add_employeeEmail);

        PopulatePositionSpinner();


        employeeID = getIntent().getIntExtra("employeeID", -1);

        addName.setText(getIntent().getStringExtra("employeeName"));
        addPhone.setText(getIntent().getStringExtra("employeePhone"));
        addEmail.setText(getIntent().getStringExtra("employeeEmail"));
        if(getIntent().getStringExtra("employeePosition") !=null){
            setEmployeePosition(EmployeePosition.valueOf(getIntent().getStringExtra("employeePosition")));
        }
    }

    private int getIndexInSpinner(Spinner spinner, String s){
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }

    private void setEmployeePosition(EmployeePosition value){
        employeePosition.setSelection(getIndexInSpinner(employeePosition, value.toString()));
    }

    private void PopulatePositionSpinner(){
        employeePosition = findViewById(R.id.employeePositionSpinner);
        positionArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, EmployeePosition.values());
        employeePosition.setAdapter(positionArrayAdapter);
    }

    private EmployeePosition getSpinnerValue() {
        return (EmployeePosition) employeePosition.getSelectedItem();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    public void isValid(){
        EntityEmployees entityEmployees;
        if (addName.getText().toString().trim().isEmpty() || addPhone.getText().toString().trim().isEmpty() | addEmail.getText().toString().trim().isEmpty() ){
            Toast.makeText(AddEmployeeActivity.this, "Make sure all fields are filled.Employee not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        else if(employeeID == -1){
            int newID = repository.getAllEmployees().get(repository.getAllEmployees().size()-1).getEmployeeID()+1;
            entityEmployees = new EntityEmployees(newID, addName.getText().toString(), getSpinnerValue(), addPhone.getText().toString(), addEmail.getText().toString());
            repository.insert(entityEmployees);
            isValid = true;
        }else{
            entityEmployees = new EntityEmployees(employeeID, addName.getText().toString(), getSpinnerValue(), addPhone.getText().toString(), addEmail.getText().toString());
            repository.update(entityEmployees);
            isValid = true;
        }
    }

    public void saveEmployeeButton(View view){
        isValid();
        finish();
        Toast.makeText(AddEmployeeActivity.this, "Employee added. Refresh page.", Toast.LENGTH_LONG).show();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.saveItem:
                isValid();
                finish();
                Toast.makeText(AddEmployeeActivity.this, "Employee added. Refresh page.", Toast.LENGTH_LONG).show();
                return true;
        }
        return  super.onOptionsItemSelected(item);
    }
}