package com.example.schedulingApp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Activities.DetailedCredentialsActivity;
import com.example.schedulingApp.Activities.DetailedDogActivity;
import com.example.schedulingApp.Entities.EntityCredentials;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityEmployees;
import com.example.schedulingApp.utils.Repository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterCredentials
    extends RecyclerView.Adapter<AdapterCredentials.ViewHolderCredentials>
    implements Filterable {

    private  List<EntityCredentials> credentialsList = new ArrayList<>();
    private final List<EntityCredentials> credentialsListFull = new ArrayList<>();
    private final Repository repository;
    private final Context context;
    private final LayoutInflater inflater;

    public AdapterCredentials(Context context, Repository repository){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.repository = repository;

        populateCredentials(repository, credentialsList);
    }

    private void populateCredentials(Repository repository, List<EntityCredentials> e){
        e.addAll(repository.getAllCredentials());
    }

    class ViewHolderCredentials extends RecyclerView.ViewHolder{
        private TextView credentialItemView;

        public ViewHolderCredentials(@NonNull View itemView){
            super(itemView);
            credentialItemView = itemView.findViewById(R.id.text_view_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final EntityCredentials current = credentialsList.get(position);
                    Intent intent = new Intent(context, DetailedCredentialsActivity.class);
                    intent.putExtra("UserID", current.getUserID());
                    intent.putExtra("UserName", current.getUserName());
                    intent.putExtra("UserPassword", current.getUserPassword());
                    intent.putExtra("employeeID", current.getEmployeeID());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public AdapterCredentials.ViewHolderCredentials onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_list, parent, false);
        return new AdapterCredentials.ViewHolderCredentials(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterCredentials.ViewHolderCredentials holder, int position) {
        if(credentialsList !=null){
            EntityCredentials currentCredentials = credentialsList.get(position);
            final int employeeID = currentCredentials.getEmployeeID();
            final EntityEmployees e = repository.findEmployeeById(employeeID);
            holder.credentialItemView.setText(e.getEmployeeName());
        }else{
            holder.credentialItemView.setText("No Credentials");
        }
    }

    @Override
    public int getItemCount() {
        if(credentialsList !=null){
            return  credentialsList.size();
        }else return  0;
    }

    public void setCredentials(List<EntityCredentials> credentialsList){
        this.credentialsList = credentialsList;
        notifyDataSetChanged();
    }

    //search bar
    public Filter getFilter(){return credentialFilter;}

    private Filter credentialFilter = new Filter() {
        private Integer employeeID;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<EntityCredentials> filteredCredentials = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0){
                filteredCredentials.addAll(credentialsListFull);
            }else{

                for(EntityCredentials item: credentialsListFull){
                    int employeeIDs = item.getEmployeeID();

                    if (employeeID == employeeIDs) {
                        filteredCredentials.add(item);
                    }
                }
            }
            results.values = filteredCredentials;
            return results;
        }
        public void filterByEmployeeID(Integer id) {
            this.employeeID = id;
            filter("");
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            credentialsList.clear();
            credentialsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
