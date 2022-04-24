package com.example.schedulingApp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schedulingApp.Activities.DetailedEmployeeActivity;
import com.example.schedulingApp.Entities.EntityEmployees;
import com.example.deliveryapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterEmployees extends RecyclerView.Adapter<AdapterEmployees.ViewHolderEmployees> {
    private List <EntityEmployees> e;
    private final Context context;
    private final LayoutInflater inflater;

    public AdapterEmployees(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    class ViewHolderEmployees extends RecyclerView.ViewHolder{
        private TextView employeeTextView;

        public ViewHolderEmployees(@NonNull View itemView) {
            super(itemView);
            employeeTextView = itemView.findViewById(R.id.text_view_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final EntityEmployees current = e.get(position);
                    Intent intent = new Intent(context, DetailedEmployeeActivity.class);
                    intent.putExtra("employeeID", current.getEmployeeID());
                    intent.putExtra("employeeName", current.getEmployeeName());
                    intent.putExtra("employeePosition", current.getEmployeePosition());
                    intent.putExtra("employeePhone", current.getEmployeePhone());
                    intent.putExtra("employeeEmail", current.getEmployeeEmail());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public AdapterEmployees.ViewHolderEmployees onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolderEmployees(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEmployees holder, int position) {
        if(e != null){
            EntityEmployees currentEmployee = e.get(position);
            holder.employeeTextView.setText(currentEmployee.getEmployeeName());
        }else{
            holder.employeeTextView.setText("No Employees");
        }
    }

    public void setEmployees(List<EntityEmployees> e){
        this.e = e;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(e!=null){
            return e.size();
        }
        else return 0;
    }
}
