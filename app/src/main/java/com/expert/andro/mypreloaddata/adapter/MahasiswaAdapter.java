package com.expert.andro.mypreloaddata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.expert.andro.mypreloaddata.MahasiswaModel;
import com.expert.andro.mypreloaddata.R;

import java.util.ArrayList;

/**
 * Created by adul on 20/09/17.
 */

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaHolder> {

    private ArrayList<MahasiswaModel> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public MahasiswaAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MahasiswaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa_row,parent,false);
        return new MahasiswaHolder(view);
    }

    public ArrayList<MahasiswaModel> getmData() {
        return mData;
    }

    public void setmData(ArrayList<MahasiswaModel> mData) {
        this.mData = mData;
    }

    public void addItem(ArrayList<MahasiswaModel> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MahasiswaHolder holder, int position) {
        holder.textViewNim.setText(mData.get(position).getNim());
        holder.textViewNama.setText(mData.get(position).getName());

        holder.textViewNama.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, ""+getmData().get(position).getNim(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MahasiswaHolder extends RecyclerView.ViewHolder {

        private TextView textViewNim;
        private TextView textViewNama;

        public MahasiswaHolder(View itemView) {
            super(itemView);
            textViewNama = itemView.findViewById(R.id.txt_nama);
            textViewNim = itemView.findViewById(R.id.txt_nim);
        }
    }
}
