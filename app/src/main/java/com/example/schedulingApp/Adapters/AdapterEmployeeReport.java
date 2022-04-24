package com.example.schedulingApp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.deliveryapp.R;
import com.example.schedulingApp.Entities.EntityDogs;
import com.example.schedulingApp.Entities.EntityEmployees;
import com.example.schedulingApp.Entities.EntityServices;
import com.example.schedulingApp.utils.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterEmployeeReport
        extends RecyclerView.Adapter<AdapterEmployeeReport.ViewHolderE>
        implements Filterable {

    private final List<EntityServices> employeesPerformed
            = new ArrayList<>();
    private final List<EntityServices> employeesPerformed_Filtered = new ArrayList<>();
    private final Repository repository;
    private final LayoutInflater inflater;
    private final Context context;

    public AdapterEmployeeReport(Context context, Repository repository){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.repository = repository;

        populateEmployeeReport(repository, employeesPerformed);
    }

    private void populateEmployeeReport(Repository repository, List<EntityServices> s){
        s.addAll(repository.getAllServices());
    }


    @NonNull
    @Override
    public ViewHolderE onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = inflater.inflate(R.layout.report_month_item_list, parent, false);
        return new ViewHolderE(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderE holder, int position){
        if(getItemCount()!=0){
            final EntityServices s = employeesPerformed_Filtered.get(position);
            holder.ERItemViewProduct.setText(s.getProduct().toString());
            holder.ERItemViewProductAddOn.setText(s.getProductAddOns().toString());

            final int dogID = s.getDogID();
            final EntityDogs dog = repository.findDogById(dogID);
            holder.ERItemViewDog.setText(dog.getDogName());

            holder.ERItemViewDate.setText(s.getServiceDate());
        }
        else{
            holder.ERItemViewProduct.setText("No Services Performed");
        }
    }

    @Override
    public int getItemCount(){
        return employeesPerformed_Filtered == null ? 0 : employeesPerformed_Filtered.size();
    }

    public FilterImpls getFilter() { return serviceFilter; }

    private final FilterImpls serviceFilter = new FilterImpls();
    public class FilterImpls extends Filter {
        private Integer employeeID;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<EntityServices> filteredService = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (employeeID == null) {
                filteredService.addAll(employeesPerformed);
            } else {

                for (EntityServices item : employeesPerformed) {

                    int employeeIDs = item.getEmployeeID();


                    if (employeeID == employeeIDs) {
                        filteredService.add(item);
                    }
                }
            }
            results.values = filteredService;
            return results;
        }


        private Date date(String date) {
            String dayFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dayFormat, Locale.US);
            try {
                return sdf.parse(date);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            employeesPerformed_Filtered.clear();
            employeesPerformed_Filtered.addAll((List) results.values);
            notifyDataSetChanged();
        }

        public void filterByEmployeeID(Integer id) {
            this.employeeID = id;
            filter("");
        }
    };

    class ViewHolderE extends RecyclerView.ViewHolder{
        private TextView ERItemViewProduct;
        private TextView ERItemViewProductAddOn;
        private TextView ERItemViewDog;
        private TextView ERItemViewDate;

        public ViewHolderE(@NonNull View itemView){
            super(itemView);
            ERItemViewProduct  = itemView.findViewById(R.id.text_view_item1);
            ERItemViewProductAddOn  = itemView.findViewById(R.id.text_view_item2);
            ERItemViewDog = itemView.findViewById(R.id.text_view_item3);
            ERItemViewDate = itemView.findViewById(R.id.text_view_item4);
        }
    }
}
