package com.example.schedulingApp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Adapters.AdapterCredentials;
import com.example.schedulingApp.Adapters.AdapterDogs;
import com.example.schedulingApp.Entities.EntityCredentials;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.utils.Repository;

import java.util.List;

public class CredentialActivity extends AppCompatActivity {
    Repository repository;
    RecyclerView recyclerView;

    List<EntityCredentials> credentialList;
    AdapterCredentials adapterCredentials;
    int UserID;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credential);
        repository = new Repository(getApplication());

        credentialList = repository.getAllCredentials();
        adapterCredentials = new AdapterCredentials(this, repository);
        UserID = getIntent().getIntExtra("UserID", -1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        credentialRecyclerView();
    }

    private void credentialRecyclerView(){
        recyclerView = findViewById(R.id.LoginRecyclerView);
        recyclerView.setAdapter(adapterCredentials);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterCredentials.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshList:
                credentialRecyclerView();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToAddLogin(View view){
        Intent intent = new Intent(CredentialActivity.this, AddCredentialsActivity.class);
        startActivity(intent);
    }

}