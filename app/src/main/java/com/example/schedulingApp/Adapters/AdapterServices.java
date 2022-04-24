package com.example.schedulingApp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schedulingApp.Activities.DetailedServicesActivity;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityServices;
import com.example.deliveryapp.R;
import com.example.schedulingApp.ViewModel.DogViewModel;
import com.example.schedulingApp.utils.Repository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterServices extends RecyclerView.Adapter<AdapterServices.ViewHolderService> {
    private List<EntityServices> servicesList = new ArrayList<>();
    private final List<EntityServices> serviceListFull = new ArrayList<>();
    private final Context context;
    private final LayoutInflater inflater;
    EntityServices current;
    private final Repository repository;


    public AdapterServices(Context context, Repository repository){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.repository = repository;
        populateServices(repository, servicesList);

    }

    private void populateServices(Repository repository, List<EntityServices> s){
        s.addAll(repository.getAllServices());
    }

    class ViewHolderService extends RecyclerView.ViewHolder{
        private TextView serviceItemViewService;
        private TextView serviceItemViewDog;
        private TextView serviceItemViewDate;
        private TextView serviceItemViewTime;

        public ViewHolderService(@NonNull View itemView){
            super(itemView);
            serviceItemViewService = itemView.findViewById(R.id.text_view_item1);
            serviceItemViewDog = itemView.findViewById(R.id.text_view_item2);
            serviceItemViewDate = itemView.findViewById(R.id.text_view_item3);
            serviceItemViewTime = itemView.findViewById(R.id.text_view_item4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 int position = getAdapterPosition();
                 current = servicesList.get(position);
                 Intent intent = new Intent(context, DetailedServicesActivity.class);
                 intent.putExtra("serviceID", current.getServiceID());
                 intent.putExtra("product", current.getProduct().name());
                 intent.putExtra("productAddOns", current.getProductAddOns().name());
                 intent.putExtra("serviceDate", current.getServiceDate());
                 intent.putExtra("serviceTime", current.getServiceTime());
                 intent.putExtra("serviceNotes", current.getServiceNotes());
                 intent.putExtra("dogID", current.getDogID());
                    intent.putExtra("employeeID", current.getEmployeeID());
                 context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public ViewHolderService onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.report_month_item_list, parent, false);
        return new ViewHolderService(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderService holder, int position) {
        if(servicesList !=null){
            EntityServices currentOrders = servicesList.get(position);
            holder.serviceItemViewService.setText(currentOrders.getProduct().toString());

            final int dogID = currentOrders.getDogID();
            final EntityDogs dog = repository.findDogById(dogID);
            holder.serviceItemViewDog.setText(dog.getDogName());
            holder.serviceItemViewDate.setText(currentOrders.getServiceDate());
            holder.serviceItemViewTime.setText(currentOrders.getServiceTime());
        }else{
            holder.serviceItemViewService.setText("No Services");
        }
    }


    public void setServices(List<EntityServices> o){
        this.servicesList = o;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(servicesList !=null){
            return servicesList.size();
        }else return 0;
    }

}
