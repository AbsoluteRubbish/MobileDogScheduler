package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityEmployees;
import com.example.schedulingApp.Entities.EntityServices;
import com.example.schedulingApp.ViewModel.DogViewModel;
import com.example.schedulingApp.ViewModel.EmployeeViewModel;
import com.example.schedulingApp.ViewModel.OwnerViewModel;
import com.example.schedulingApp.ViewModel.ProductAddOns;
import com.example.schedulingApp.ViewModel.Products;
import com.example.schedulingApp.utils.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddServiceActivity extends AppCompatActivity {

    Repository repository;

    int serviceID;
    Spinner serviceProduct;
    private ArrayAdapter<Products> productAdapter;
    Spinner serviceAddOns;
    private ArrayAdapter<ProductAddOns> addOnsAdapter;
    EditText serviceDate;
    EditText serviceTime;
    EditText serviceNotes;

    private int employeeID;
    Spinner serviceEmployee;
    private ArrayAdapter<EmployeeViewModel> employeeAdapter;

    private int dogID;
    Spinner serviceDog;
    private ArrayAdapter<DogViewModel> dogAdapter;
    boolean isValid;

    DatePickerDialog.OnDateSetListener apptDate;
    TimePickerDialog apptTime;


    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services);
        repository = new Repository(getApplication());


        serviceProduct = findViewById(R.id.add_product);
        serviceAddOns = findViewById(R.id.add_addOns);
        serviceDate = findViewById(R.id.add_date);
        serviceTime = findViewById(R.id.add_time);
        serviceNotes = findViewById(R.id.add_notes);
        serviceEmployee = findViewById(R.id.add_serviceEmployee);
        serviceDog = findViewById(R.id.add_serviceDog);

        Bundle b = getIntent().getExtras();

        PopulateAllSpinners();

        serviceID = getIntent().getIntExtra("serviceID", -1);
        serviceNotes.setText(getIntent().getStringExtra("serviceNotes"));

        if(getIntent().getStringExtra("product")!= null){
            setProduct(Products.valueOf(getIntent().getStringExtra("product")));
        }
        if (getIntent().getStringExtra("productAddOns") != null) {
            setProductAddOns(ProductAddOns.valueOf(getIntent().getStringExtra("productAddOns")));
        }

        employeeID = getIntent().getIntExtra("employeeID", -1);
        serviceEmployee.setSelection(indexInEmployeeSpinner(employeeID));
        if(employeeID != -1){
            b.getInt("employeeID");
        }

        dogID = getIntent().getIntExtra("dogID", -1);
        serviceDog.setSelection(indexInDogSpinner(dogID));
        if(dogID != -1){
            b.getInt("dogID");
        }

//date
        final Calendar calendar = Calendar.getInstance();
        apptDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);

                serviceDate.setText(sdf.format(calendar.getTime()));
            }

        };

        serviceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddServiceActivity.this, apptDate, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        apptDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                serviceDate.setText(sdf.format(calendar.getTime()));
            }
        };

//time
        final Calendar timeCalendar = Calendar.getInstance();
        int hour = timeCalendar.get(Calendar.HOUR_OF_DAY);
        int minutes = timeCalendar.get(Calendar.MINUTE);
        serviceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                apptTime = new TimePickerDialog(AddServiceActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        serviceTime.setText( h + ":" + convertDate(m));
                    }
                }, hour, minutes, true);
        apptTime.show();
            }

        });

    }

    public String convertDate(int input){
        if(input >= 10){
            return String.valueOf(input);
        }else{
            return "0" + String.valueOf(input);
        }
    }
    private void PopulateAllSpinners(){
        //products
        productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Products.values());
        serviceProduct.setAdapter(productAdapter);
        //addOns
        addOnsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ProductAddOns.values());
        serviceAddOns.setAdapter(addOnsAdapter);
        //employees
        List<EmployeeViewModel> spinnerEmployeeName = getEmployeeViewModel();
        employeeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerEmployeeName);
        employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceEmployee.setAdapter(employeeAdapter);
        //dogs
        List<DogViewModel> spinnerDogName = getDogViewModel();
        dogAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerDogName);
        dogAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceDog.setAdapter(dogAdapter);
    }

    private List<EmployeeViewModel> getEmployeeViewModel(){
        List<EntityEmployees> employeeName = repository.getAllEmployees();
        List<EmployeeViewModel> e = new ArrayList<>();
        for (int i=0; i<employeeName.size(); i++){
            e.add(new EmployeeViewModel(employeeName.get(i)));
        }
        return e;
    }
    private List<DogViewModel> getDogViewModel(){
        List<EntityDogs> dogName = repository.getAllDogs();
        List<DogViewModel> d = new ArrayList<>();
        for (int i=0; i<dogName.size(); i++){
            d.add(new DogViewModel(dogName.get(i)));
        }
        return d;
    }

    private void setProduct(Products value){
        serviceProduct.setSelection(getIndexInSpinner(serviceProduct, value.toString()));
    }

    private void setProductAddOns(ProductAddOns value){
        serviceAddOns.setSelection(getIndexInSpinner(serviceAddOns, value.toString()));
    }

    private int getIndexInSpinner(Spinner spinner, String s){
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }

    private int indexInEmployeeSpinner(int employeeID){
        for(int i=0; i<serviceEmployee.getCount(); i++){
            EmployeeViewModel item = (EmployeeViewModel) serviceEmployee.getItemAtPosition(i);
            if(item.id == employeeID){
                return i;
            }
        }
        return -1;
    }
    private int indexInDogSpinner(int dogID){
        for(int i=0; i<serviceDog.getCount(); i++){
            DogViewModel item = (DogViewModel) serviceDog.getItemAtPosition(i);
            if(item.id == dogID){
                return i;
            }
        }
        return -1;
    }

    private Products SpinnerProductValue(){
        return (Products) serviceProduct.getSelectedItem();
    }

    private ProductAddOns SpinnerAddOnValue(){
        return (ProductAddOns) serviceAddOns.getSelectedItem();
    }

    private int SpinnerEmployeeIDValue(){
        EmployeeViewModel item = (EmployeeViewModel) serviceEmployee.getSelectedItem();
        return item.id;
    }
    private int SpinnerDogIDValue(){
        DogViewModel item = (DogViewModel) serviceDog.getSelectedItem();
        return item.id;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    public void isValid(){
        EntityServices entityServices;
        if(serviceDate.getText().toString().trim().isEmpty() || serviceTime.getText().toString().trim().isEmpty() ||serviceNotes.getText().toString().trim().isEmpty()){
            Toast.makeText(AddServiceActivity.this, "Make sure fields are not empty. Service not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        else if(serviceID == -1){
            int newID = repository.getAllServices().get(repository.getAllServices().size()-1).getServiceID()+1;
            entityServices = new EntityServices(newID,SpinnerProductValue(), SpinnerAddOnValue(), serviceDate.getText().toString(), serviceTime.getText().toString(), serviceNotes.getText().toString(), SpinnerDogIDValue(), SpinnerEmployeeIDValue());
            repository.insert(entityServices);
            isValid = true;
        }else {
            entityServices = new EntityServices(serviceID,SpinnerProductValue(), SpinnerAddOnValue(), serviceDate.getText().toString(), serviceTime.getText().toString(), serviceNotes.getText().toString(), SpinnerDogIDValue(), SpinnerEmployeeIDValue());
            repository.insert(entityServices);
            isValid = true;
        }
    }
    public void saveServiceButton(View view){
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