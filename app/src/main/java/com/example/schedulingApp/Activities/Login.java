package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Entities.EntityCredentials;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityEmployees;
import com.example.schedulingApp.Entities.EntityOwners;
import com.example.schedulingApp.Entities.EntityServices;
import com.example.schedulingApp.ViewModel.DogAggression;
import com.example.schedulingApp.ViewModel.DogWeight;
import com.example.schedulingApp.ViewModel.EmployeePosition;
import com.example.schedulingApp.ViewModel.ProductAddOns;
import com.example.schedulingApp.ViewModel.Products;
import com.example.schedulingApp.utils.Repository;

public class Login extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginbtn;
    TextView attemptInfo;

    String user;
    String uPassword;

    int counter = 5;

    EntityCredentials credentials;
    EntityCredentials staffCredentials;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        repository = new Repository(getApplication());

        repository.insert(new EntityEmployees(1, "Deville", EmployeePosition.MANAGER, "666-666-6666", "deville@gmail.com"));
        repository.insert(new EntityEmployees(2, "Jasper", EmployeePosition.GROOMER, "111-222-3333", "jasper101@gmail.com"));
        repository.insert(new EntityEmployees(3, "Horace", EmployeePosition.BATHER, "555-666-1234", "horace102@gmail.com"));

        repository.insert(new EntityOwners(1, "Anita Dearly", "123 london st", "999-999-9999", "dearly101@gmail.com"));
        repository.insert(new EntityOwners(2, "Roger Radcliffe", "456 london st", "888-888-8888", "radcliffe101@gmail.com"));
        repository.insert(new EntityOwners(3, "Jim Dear", "852 marceline st", "777-777-7777", "DarlingL&T@gmail.com"));
        repository.insert(new EntityOwners(4, "Jenny Foxworth", "746 new york st", "666-666-6666", "foxworth&company@gmail.com"));
        repository.insert(new EntityOwners(5, "George Darling", "789 bloomsbury st", "555-555-5555", "DarlingPeterPan@gmail.com"));

        repository.insert(new EntityDogs(1, "Pongo", "Dalmatian", DogAggression.YES, DogWeight.LARGE, 2));
        repository.insert(new EntityDogs(2, "Perdita", "Dalmatian", DogAggression.ONLY_MEN, DogWeight.MEDIUM, 1));
        repository.insert(new EntityDogs(3, "Patch", "Dalmatian", DogAggression.UNKNOWN, DogWeight.MEDIUM, 1));//Perdita child under antia
        repository.insert(new EntityDogs(4, "Tramp", "Mutt", DogAggression.NO, DogWeight.MEDIUM, 3));
        repository.insert(new EntityDogs(5, "Lady", "Cocker Spaniel", DogAggression.NAILS, DogWeight.SMALL, 3));
        repository.insert(new EntityDogs(6, "Scamp", "Mutt", DogAggression.UNKNOWN, DogWeight.SMALL, 3));
        repository.insert(new EntityDogs(7, "Georgette", "Poodle", DogAggression.NO, DogWeight.LARGE, 4));
        repository.insert(new EntityDogs(8, "Dodger", "terrier mix", DogAggression.ONLY_WOMEN, DogWeight.MEDIUM, 4));
        repository.insert(new EntityDogs(9, "Tito", "Chihuahua", DogAggression.YES, DogWeight.SMALL, 4));
        repository.insert(new EntityDogs(10, "Nana", "Newfoundlands", DogAggression.NO, DogWeight.XXLARGE, 5));

        repository.insert(new EntityServices(1, Products.FULL_PACKAGE, ProductAddOns.NONE, "4/1/22", "10:00", "Likes Deville", 2, 1));
        repository.insert(new EntityServices(2, Products.BATH, ProductAddOns.NAIL_TRIMMING, "4/1/22", "10:30", "Perdita's child", 3, 1));
        repository.insert(new EntityServices(3, Products.BATH_DELUXE, ProductAddOns.HAIR_AND_GLANDS, "4/5/22", "11:00", "Sprayed by skunk",4, 3));
        repository.insert(new EntityServices(4, Products.GROOMING_DELUXE, ProductAddOns.ALL_Three, "4/10/22", "09:00", "Show dog cut", 7, 2));
        repository.insert(new EntityServices(5, Products.GROOMING, ProductAddOns.NAIL_TRIMMING, "4/15/22", "13:00", "owner wants completely shaved", 10, 2 ));
        repository.insert(new EntityServices(6, Products.BATH, ProductAddOns.HAIR_AND_GLANDS, "4/08/22", "12:00", "Flea bath", 8, 3));

        repository.insert(credentials = new EntityCredentials(1,"Admin", "1234", 1));
        repository.insert(staffCredentials = new EntityCredentials(2, "Staff", "4567", 2));

        username = findViewById(R.id.userNameEdit);
        password = findViewById(R.id.passwordEdit);
        loginbtn = findViewById(R.id.LoginButton);
        attemptInfo = findViewById(R.id.attempts);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString();
                uPassword = password.getText().toString();

                isValid ();
                if(isValid() == false){
                    counter--;
                    attemptInfo.setText("Attempts Remaining: " + String.valueOf(counter));
                    if(counter == 0){
                        loginbtn.setEnabled(false);
                        Toast.makeText(Login.this, "All attempts used, try again later", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(Login.this, "Incorrect credentials, try again.", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

    }

    public boolean isValid(){
        if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            Toast.makeText(Login.this, "Please Enter UserName and Password", Toast.LENGTH_LONG).show();
            return true;
        }
        else if(username.getText().toString().equals(credentials.getUserName()) && password.getText().toString().equals(credentials.getUserPassword())){
            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if(username.getText().toString().equals(staffCredentials.getUserName()) && password.getText().toString().equals(staffCredentials.getUserPassword())){
            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Login.this, MainActivityStaff.class);
            startActivity(intent);
            return true;
        }else{
            Toast.makeText(Login.this, "Login Fail", Toast.LENGTH_LONG).show();
            return false;
        }

    }

}