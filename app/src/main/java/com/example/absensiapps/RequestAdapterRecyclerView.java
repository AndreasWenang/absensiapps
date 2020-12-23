package com.example.absensiapps;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RequestAdapterRecyclerView extends RecyclerView.Adapter<RequestAdapterRecyclerView.MyViewHolder> {

    private List<Requests> moviesList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout;
        public TextView tv_title, tv_email, tv_jurusan;

        public MyViewHolder(View view) {
            super(view);
            rl_layout = view.findViewById(R.id.rl_layout);
            tv_title = view.findViewById(R.id.tv_title);
            tv_email = view.findViewById(R.id.tv_email);
            tv_jurusan = view.findViewById(R.id.tv_jurusan);
        }
    }

    public RequestAdapterRecyclerView(List<Requests> moviesList, Activity activity) {
        this.moviesList = moviesList;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Requests movie = moviesList.get(position);

        holder.tv_title.setText(movie.getNama());
        holder.tv_email.setText(movie.getKelas());
        holder.tv_jurusan.setText(movie.getJurusan());

        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetail = new Intent(mActivity, percobaan.class);
                goDetail.putExtra("id", movie.getKey());
                goDetail.putExtra("title", movie.getNama());
                goDetail.putExtra("kelas", movie.getKelas());
                goDetail.putExtra("jurusan", movie.getJurusan());

                mActivity.startActivity(goDetail);


            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


}
