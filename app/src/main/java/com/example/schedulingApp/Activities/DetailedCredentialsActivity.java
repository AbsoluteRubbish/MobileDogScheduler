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

public class DetailedCredentialsActivity extends AppCompatActivity {

    Repository repository;

    int userID;
    EditText userName;
    EditText userPassword;

    private int employeeID;
    Spinner spinnerEmployee;
    private ArrayAdapter<EmployeeViewModel> employeeAdapter;
    boolean isValid;
    EntityCredentials currentCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_credentials);
        repository = new Repository(getApplication());

        userName = findViewById(R.id.detailed_userName);
        userPassword = findViewById(R.id.detailed_userPassword);
        spinnerEmployee = findViewById(R.id.detailed_credentails_employee);

        Bundle b = getIntent().getExtras();

        List<EmployeeViewModel> spinnerEmployeeName = getEmployeeViewModel();
        employeeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerEmployeeName);
        employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmployee.setAdapter(employeeAdapter);


        userID = getIntent().getIntExtra("UserID", -1);

        employeeID = getIntent().getIntExtra("employeeID", -1);
        spinnerEmployee.setSelection(indexInEmployeeSpinner(employeeID));
        userName.setText(getIntent().getStringExtra("UserName"));
        userPassword.setText(getIntent().getStringExtra("UserPassword"));

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
        for(int i=0; i<spinnerEmployee.getCount(); i++){
            EmployeeViewModel item = (EmployeeViewModel) spinnerEmployee.getItemAtPosition(i);
            if(item.id == employeeID){
                return i;
            }
        }
        return -1;
    }

    private int SpinnerEmployeeIDValue(){
        EmployeeViewModel item = (EmployeeViewModel) spinnerEmployee.getSelectedItem();
        return item.id;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_delete, menu);
        return true;
    }

    public void isValid(){
        EntityCredentials entityCredentials;
        if(userName.getText().toString().trim().isEmpty() || userPassword.getText().toString().trim().isEmpty()){
            Toast.makeText(DetailedCredentialsActivity.this, "Make sure fields are not empty. Credentials not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }else {
        entityCredentials = new EntityCredentials(userID,userName.getText().toString(), userPassword.getText().toString(), SpinnerEmployeeIDValue());
            repository.update(entityCredentials);
            Toast.makeText(DetailedCredentialsActivity.this, "Credentials updated, please refresh page.", Toast.LENGTH_LONG).show();
            isValid = true;
        }
    }
    public void canDelete(){
        for (EntityCredentials credentials : repository.getAllCredentials()){
            if(credentials.getUserID() == userID) currentCredentials = credentials;
            repository.delete(currentCredentials);
            Toast.makeText(DetailedCredentialsActivity.this, currentCredentials.getUserName() + " was deleted. Refresh Page.", Toast.LENGTH_LONG).show();
        }
    }

    public void saveDetailedCredentialsButton(View view){
        isValid();
        finish();
    }

    public void deleteCredentialsButton(View view){
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
        }
        return super.onOptionsItemSelected(item);
    }

}