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
import com.example.schedulingApp.Activities.DetailedOwnerActivity;
import com.example.schedulingApp.Entities.EntityOwners;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterOwners extends RecyclerView.Adapter<AdapterOwners.ViewHolderOwners> implements Filterable {

    private final Context context;
    private final LayoutInflater inflater;

    private final List<EntityOwners> ownersList;
    private final List<EntityOwners> ownerListFull;



    public AdapterOwners(Context context, List<EntityOwners> ownersList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.ownersList = ownersList;
        ownerListFull = new ArrayList<>(ownersList);

    }

    class ViewHolderOwners extends RecyclerView.ViewHolder{
        private TextView ownerTextView;

        public ViewHolderOwners(@NonNull View itemView){
            super(itemView);
            ownerTextView = itemView.findViewById(R.id.text_view_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final EntityOwners current = ownersList.get(position);
                    Intent intent = new Intent(context, DetailedOwnerActivity.class);
                    intent.putExtra("ownerID", current.getOwnerID());
                    intent.putExtra("ownerName", current.getOwnerName());
                    intent.putExtra("ownerAddress", current.getOwnerAddress());
                    intent.putExtra("ownerPhone", current.getOwnerPhone());
                    intent.putExtra("ownerEmail", current.getOwnerEmail());
                    context.startActivity(intent);
                }
            });
        }
    }


    @NonNull
    @Override
    public ViewHolderOwners onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolderOwners(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOwners.ViewHolderOwners holder, int position) {
        if(ownersList !=null){
            EntityOwners currentOwners = ownersList.get(position);
            holder.ownerTextView.setText(currentOwners.getOwnerName());
        }else{
            holder.ownerTextView.setText("No Clients");
        }
    }

    @Override
    public int getItemCount() {
        if(ownersList !=null){
            return  ownersList.size();
        }else return  0;
    }

    //new for search bar

    public Filter getFilter(){
        return ownerFilter;
    }

    private Filter ownerFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<EntityOwners> filteredOwners = new ArrayList<>();
            FilterResults results = new FilterResults();

             if(constraint == null || constraint.length() == 0){
               filteredOwners.addAll(ownerListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(EntityOwners item : ownerListFull){
                    if (item.getOwnerName().toLowerCase().contains(filterPattern)){
                        filteredOwners.add(item);
                    }
                }
            }
            results.values = filteredOwners;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            ownersList.clear();
            ownersList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
