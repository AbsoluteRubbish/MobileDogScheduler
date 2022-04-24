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
import com.example.schedulingApp.Entities.EntityServices;
import com.example.schedulingApp.utils.Repository;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterMonth
        extends RecyclerView.Adapter<AdapterMonth.ViewHolderMonth>
        implements Filterable {

    private final List<EntityServices> servicesPerformed
            = new ArrayList<>();
    private final List<EntityServices> servicesPerformed_Filtered = new ArrayList<>();
    private final Repository repository;
    private final LayoutInflater inflater;
    private final Context context;

    public AdapterMonth(Context context, Repository repository) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.repository = repository;

        populateServices(repository, servicesPerformed);
    }

    private void populateServices(Repository repository, List<EntityServices> o) {
        o.addAll(repository.getAllServices());
    }

    @NonNull
    @Override
    public ViewHolderMonth onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.report_month_item_list, parent, false);
        return new ViewHolderMonth(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMonth holder, int position) {
        if (getItemCount() != 0) {
            final EntityServices s = servicesPerformed_Filtered.get(position);
            holder.monthItemViewproduct.setText(s.getProduct().toString());
            holder.monthItemViewDate.setText(s.getServiceDate());
            holder.monthItemViewTime.setText(s.getServiceTime());

            final int dogID = s.getDogID();
            final EntityDogs doog = repository.findDogById(dogID);

            holder.monthItemViewdog.setText(doog.getDogName());
        } else {
            holder.monthItemViewdog.setText("No Services this month");
        }
    }

    @Override
    public int getItemCount() {
        return servicesPerformed_Filtered == null ? 0 : servicesPerformed_Filtered.size();
    }

    public FilterImpl getFilter() {
        return serviceFilter;
    }

    private final FilterImpl serviceFilter = new FilterImpl();
        public class FilterImpl extends Filter {
            private Month month;

            @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<EntityServices> filteredService = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (month == null) {
                filteredService.addAll(servicesPerformed);
            } else {

                for (EntityServices item : servicesPerformed) {

                    Date serviceDate = date(item.getServiceDate());

                    if (serviceDate.getMonth() == month.ordinal()){
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
            servicesPerformed_Filtered.clear();
            servicesPerformed_Filtered.addAll((List)results.values);
            notifyDataSetChanged();
        }

        public void filterByMonth(Month value) {
            this.month = value;
            filter("");
            }
        };

    class ViewHolderMonth extends RecyclerView.ViewHolder {
        private TextView monthItemViewdog;
        private TextView monthItemViewproduct;
        private TextView monthItemViewDate;
        private TextView monthItemViewTime;

        public ViewHolderMonth(@NonNull View itemView) {
            super(itemView);
            monthItemViewdog = itemView.findViewById(R.id.text_view_item1);
            monthItemViewproduct = itemView.findViewById(R.id.text_view_item2);
            monthItemViewDate = itemView.findViewById(R.id.text_view_item3);
            monthItemViewTime = itemView.findViewById(R.id.text_view_item4);
        }
    }
}
