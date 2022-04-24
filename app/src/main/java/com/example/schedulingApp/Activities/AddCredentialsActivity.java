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
import com.example.schedulingApp.Entities.EntityCredentials;
import com.example.schedulingApp.Entities.EntityEmployees;
import com.example.schedulingApp.Entities.EntityServices;
import com.example.schedulingApp.ViewModel.EmployeeViewModel;
import com.example.schedulingApp.utils.Repository;

import java.util.ArrayList;
import java.util.List;

public class AddCredentialsActivity extends AppCompatActivity {

    Repository repository;

    int userID;
    EditText addUserName;
    EditText addUserPassword;

    private int employeeID;
    Spinner addEmployee;
    private ArrayAdapter<EmployeeViewModel> employeeAdapter;

    boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credentials);
        repository = new Repository(getApplication());

        addUserName = findViewById(R.id.add_userName);
        addUserPassword = findViewById(R.id.add_userPassword);
        addEmployee = findViewById(R.id.add_employee_credential_spinner);

        Bundle b = getIntent().getExtras();

        List<EmployeeViewModel> spinnerEmployeeName = getEmployeeViewModel();
        employeeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerEmployeeName);
        employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addEmployee.setAdapter(employeeAdapter);

        employeeID = getIntent().getIntExtra("employeeID", -1);
        addEmployee.setSelection(indexInEmployeeSpinner(employeeID));
        if(employeeID != -1){
            b.getInt("employeeID");
        }

    }

    private List<EmployeeViewModel> getEmployeeViewModel(){
        List<EntityEmployees> employeeName = repository.getAllEmployees();
        List<EmployeeViewModel> e = new ArrayList<>();
        for (int i=0; i<employeeName.size(); i++){
            e.add(new EmployeeViewModel(employeeName.get(i)));
        }
        return e;
    }
    private int indexInEmployeeSpinner(int employeeID){
        for(int i=0; i<addEmployee.getCount(); i++){
            EmployeeViewModel item = (EmployeeViewModel) addEmployee.getItemAtPosition(i);
            if(item.id == employeeID){
                return i;
            }
        }
        return -1;
    }
    private int SpinnerEmployeeIDValue(){
        EmployeeViewModel item = (EmployeeViewModel) addEmployee.getSelectedItem();
        return item.id;
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
    public void isValid(){
        EntityCredentials entityCredentials;
        if(addUserName.getText().toString().trim().isEmpty() || addUserPassword.getText().toString().trim().isEmpty()){
            Toast.makeText(AddCredentialsActivity.this, "Make sure fields are not empty. Credentials not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        else if(userID == -1){
            int newID = repository.getAllServices().get(repository.getAllServices().size()-1).getServiceID()+1;
            entityCredentials = new EntityCredentials(newID, addUserName.getText().toString(), addUserPassword.getText().toString(), SpinnerEmployeeIDValue());
            repository.insert(entityCredentials);
            isValid = true;
        }else {
            entityCredentials = new EntityCredentials(userID,addUserName.getText().toString(), addUserPassword.getText().toString(), SpinnerEmployeeIDValue());
            repository.insert(entityCredentials);
            isValid = true;
        }
    }
    public void saveCredentialsButton(View view){
        isValid();
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
        }
        return  super.onOptionsItemSelected(item);
    }
}