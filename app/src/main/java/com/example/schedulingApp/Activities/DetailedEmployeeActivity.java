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
import com.example.schedulingApp.ViewModel.EmployeePosition;
import com.example.schedulingApp.utils.Repository;

public class DetailedEmployeeActivity extends AppCompatActivity {

    Repository repository;

    int employeeID;
    EditText employeeName;
    EditText employeePhone;
    EditText employeeEmail;
    Spinner employeePosition;
    private ArrayAdapter<EmployeePosition> positionArrayAdapter;

    boolean isValid;
    EntityEmployees currentEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new Repository(getApplication());
        setContentView(R.layout.activity_detailed_employee);
        employeeName = findViewById(R.id.edit_detailed_employeeName);
        employeePhone = findViewById(R.id.edit_detailed_employeePhone);
        employeeEmail = findViewById(R.id.edit_detailed_employeeEmail);

        PopulatePositionSpinner();


        employeeID = getIntent().getIntExtra("employeeID", -1);
        employeeName.setText(getIntent().getStringExtra("employeeName"));
        employeePhone.setText(getIntent().getStringExtra("employeePhone"));
        employeeEmail.setText(getIntent().getStringExtra("employeeEmail"));
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
        employeePosition = findViewById(R.id.employeeEditPositionSpinner);
        positionArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, EmployeePosition.values());
        employeePosition.setAdapter(positionArrayAdapter);
    }

    private EmployeePosition getSpinnerValue() {
        return (EmployeePosition) employeePosition.getSelectedItem();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_delete, menu);
        return true;
    }

    public void isValid(){
        EntityEmployees entityEmployees;
        if (employeeName.getText().toString().trim().isEmpty() || employeePhone.getText().toString().trim().isEmpty() | employeeEmail.getText().toString().trim().isEmpty() ){
            Toast.makeText(DetailedEmployeeActivity.this, "Make sure all fields are filled.Employee not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }else{
            entityEmployees = new EntityEmployees(employeeID, employeeName.getText().toString(), getSpinnerValue(), employeePhone.getText().toString(), employeeEmail.getText().toString());
            repository.update(entityEmployees);
            Toast.makeText(DetailedEmployeeActivity.this, "Employee updated, please refresh page.", Toast.LENGTH_LONG).show();
            isValid = true;
        }
    }

    public void canDelete(){
        for (EntityEmployees employees : repository.getAllEmployees()) {
            if (employees.getEmployeeID() == employeeID) {
                currentEmployee = employees;
                repository.delete(currentEmployee);
                Toast.makeText(DetailedEmployeeActivity.this, currentEmployee.getEmployeeName() + " was deleted. Refresh Page.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void saveDetailedEmployeeButton(View view){
        isValid();
        finish();
    }

    public void deleteEmployeeButton(View view){
        canDelete();
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
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
        }
        return  super.onOptionsItemSelected(item);
    }
}