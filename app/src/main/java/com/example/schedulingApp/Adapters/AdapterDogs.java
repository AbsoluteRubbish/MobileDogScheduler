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
import com.example.schedulingApp.Activities.DetailedDogActivity;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityEmployees;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterDogs extends RecyclerView.Adapter<AdapterDogs.ViewHolderDogs> implements Filterable {
    private  List<EntityDogs> dogsList;
    private final List<EntityDogs> dogsListFull;

    private final Context context;
    private final LayoutInflater inflater;

    public AdapterDogs(Context context, List<EntityDogs> dogsList){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.dogsList = dogsList;
        dogsListFull = new ArrayList<>(dogsList);
    }

    class ViewHolderDogs extends RecyclerView.ViewHolder{
        private TextView dogItemView;

        public ViewHolderDogs(@NonNull View itemView){
            super(itemView);
            dogItemView = itemView.findViewById(R.id.text_view_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final EntityDogs current = dogsList.get(position);
                    Intent intent = new Intent(context, DetailedDogActivity.class);
                    intent.putExtra("dogID", current.getDogID());
                    intent.putExtra("dogName", current.getDogName());
                    intent.putExtra("dogAggression", current.getDogAggression().name());
                    intent.putExtra("dogWeight", current.getDogWeight().name());
                    intent.putExtra("dogBreed", current.getDogBreed());
                    intent.putExtra("ownerID", current.getOwnerID());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public AdapterDogs.ViewHolderDogs onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolderDogs(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterDogs.ViewHolderDogs holder, int position) {
        if(dogsList !=null){
            EntityDogs currentDogs = dogsList.get(position);
            holder.dogItemView.setText(currentDogs.getDogName());
        }else{
            holder.dogItemView.setText("No Clients");
        }
    }

    @Override
    public int getItemCount() {
        if(dogsList !=null){
            return  dogsList.size();
        }else return  0;
    }

    public void setDog(List<EntityDogs> dogsList){
        this.dogsList = dogsList;
        notifyDataSetChanged();
    }

    //search bar
    public Filter getFilter(){return dogFilter;}

    private Filter dogFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<EntityDogs> filteredDogs = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0){
                filteredDogs.addAll(dogsListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(EntityDogs item: dogsListFull){
                    if(item.getDogName().toLowerCase().contains(filterPattern)){
                        filteredDogs.add(item);
                    }
                }
            }
            results.values = filteredDogs;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            dogsList.clear();
            dogsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
