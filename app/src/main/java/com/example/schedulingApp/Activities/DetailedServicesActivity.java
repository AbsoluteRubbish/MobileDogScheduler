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
import com.example.schedulingApp.ViewModel.ProductAddOns;
import com.example.schedulingApp.ViewModel.Products;
import com.example.schedulingApp.utils.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DetailedServicesActivity extends AppCompatActivity {

    Repository repository;

    EditText serviceDate;
    EditText serviceTime;
    EditText serviceNotes;

    int serviceID;
    Spinner serviceProduct;
    private ArrayAdapter<Products> productAdapter;

    Spinner serviceAddOns;
    private ArrayAdapter<ProductAddOns> addOnsAdapter;

    private int employeeID;
    Spinner serviceEmployee;
    private ArrayAdapter<EmployeeViewModel> employeeAdapter;

    private int dogID;
    Spinner serviceDog;
    private ArrayAdapter<DogViewModel> dogAdapter;

    boolean isValid;
    EntityServices currentServices;


    DatePickerDialog.OnDateSetListener apptDate;
    TimePickerDialog apptTime;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_services);
        repository = new Repository(getApplication());


        serviceAddOns = findViewById(R.id.edit_detailed_addOns);
        serviceDate = findViewById(R.id.edit_detailed_date);
        serviceTime = findViewById(R.id.edit_detailed_time);
        serviceNotes = findViewById(R.id.edit_detailed_notes);
        serviceEmployee = findViewById(R.id.edit_detailed_serviceEmployee);
        serviceDog = findViewById(R.id.edit_detailed_serviceDog);

        Bundle b = getIntent().getExtras();

        PopulateAllSpinners();

        serviceID = getIntent().getIntExtra("serviceID", -1);


        if(getIntent().getStringExtra("product")!= null){
            setProduct(Products.valueOf(getIntent().getStringExtra("product")));
            System.out.println(serviceProduct);
        }

        if (getIntent().getStringExtra("productAddOns") != null) {
            setProductAddOns(ProductAddOns.valueOf(getIntent().getStringExtra("productAddOns")));
        }

        employeeID = getIntent().getIntExtra("employeeID", -1);
        serviceEmployee.setSelection(indexInEmployeeSpinner(employeeID));


        dogID = getIntent().getIntExtra("dogID", -1);
        serviceDog.setSelection(indexInDogSpinner(dogID));


        serviceNotes.setText(getIntent().getStringExtra("serviceNotes"));
        serviceDate.setText(getIntent().getStringExtra("serviceDate"));
        serviceTime.setText(getIntent().getStringExtra("serviceTime"));

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
                new DatePickerDialog(DetailedServicesActivity.this, apptDate, calendar.get(Calendar.YEAR),
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

                apptTime = new TimePickerDialog(DetailedServicesActivity.this,
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

    private void setProduct(Products value){
        serviceProduct.setSelection(getIndexInSpinner(serviceProduct, value.toString()));
    }

    private void setProductAddOns(ProductAddOns value){
        serviceAddOns.setSelection(getIndexInSpinner(serviceAddOns, value.toString()));
    }

    private void PopulateAllSpinners(){
        //products
        serviceProduct = findViewById(R.id.edit_detailed_product);
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
        getMenuInflater().inflate(R.menu.menu_add_delete, menu);
        return true;
    }

    public void isValid(){
        EntityServices entityServices;
        if(serviceDate.getText().toString().trim().isEmpty() || serviceTime.getText().toString().trim().isEmpty() ||serviceNotes.getText().toString().trim().isEmpty()){
            Toast.makeText(DetailedServicesActivity.this, "Make sure fields are not empty. Service not saved.", Toast.LENGTH_LONG).show();
            isValid = false;
        }else {
            entityServices = new EntityServices(serviceID,SpinnerProductValue(), SpinnerAddOnValue(), serviceDate.getText().toString(), serviceTime.getText().toString(), serviceNotes.getText().toString(),SpinnerDogIDValue(), SpinnerEmployeeIDValue());
            repository.update(entityServices);
            Toast.makeText(DetailedServicesActivity.this, "Service updated, please refresh page.", Toast.LENGTH_LONG).show();
            isValid = true;
        }
    }

    public void canDelete(){
        for (EntityServices services : repository.getAllServices()){
            if(services.getServiceID() == serviceID) currentServices = services;
            repository.delete(currentServices);
            Toast.makeText(DetailedServicesActivity.this, currentServices.getProduct().toString() + " was deleted. Refresh Page.", Toast.LENGTH_LONG).show();
        }
    }

    public void saveDetailedServiceButton(View view){
        isValid();
        finish();
    }

    public void deleteServiceButton(View view){
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