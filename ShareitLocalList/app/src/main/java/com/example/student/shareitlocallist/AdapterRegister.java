package com.example.student.shareitlocallist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterRegister extends RecyclerView.Adapter<AdapterRegister.MyViewHolder> {

    private final Context context;
    private final List<ListRegister> list;

    AdapterRegister(final Context context, final List<ListRegister> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterRegister.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRegister.MyViewHolder holder, int position) {
        holder.textSurname.setText(list.get(position).getSurname());
        holder.textName.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final TextView textSurname;

        private MyViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.name);
            textSurname = itemView.findViewById(R.id.surname);
        }
    }

}
