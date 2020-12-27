package com.example.absensiapps;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.absensiapps.databinding.ItemRequestBinding;

import java.util.List;

public class RequestAdapterRecyclerView extends RecyclerView.Adapter<RequestAdapterRecyclerView.MyViewHolder> {

    private List<Requests> requestsList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRequestBinding binding2;

        public LinearLayout rl_layout;

        public MyViewHolder(ItemRequestBinding binding2) {
            super(binding2.getRoot());
            this.binding2=binding2;
        }
    }

    public RequestAdapterRecyclerView(List<Requests> requestsList, Activity activity) {
        this.requestsList = requestsList;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemRequestBinding binding2= ItemRequestBinding.inflate(layoutInflater, parent, false);
            return new MyViewHolder(binding2);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Requests requests = requestsList.get(position);
        holder.binding2.setRequests(requests);
        holder.binding2.tvTitle.setText(requests.getNama());
        holder.binding2.tvEmail.setText(requests.getKelas());
        holder.binding2.tvJurusan.setText(requests.getJurusan());

        holder.binding2.rlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetail = new Intent(mActivity, Input.class);
                goDetail.putExtra("id", requests.getKey());
                goDetail.putExtra("title", requests.getNama());
                goDetail.putExtra("kelas", requests.getKelas());
                goDetail.putExtra("jurusan", requests.getJurusan());

                mActivity.startActivity(goDetail);


            }
        });

    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }


}
