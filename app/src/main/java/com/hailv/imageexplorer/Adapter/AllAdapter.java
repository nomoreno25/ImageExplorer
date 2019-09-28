package com.hailv.imageexplorer.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hailv.imageexplorer.Fragment.EditImageFragment;
import com.hailv.imageexplorer.Modal.ImageDataModel;
import com.hailv.imageexplorer.R;

import java.util.ArrayList;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ViewHolder> {
    private ArrayList<ImageDataModel> mData;
    private LayoutInflater mInflater;
    private Context context;
    public static int index=0;

    public AllAdapter(Context context, ArrayList<ImageDataModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_all,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load("file://"+mData.get(i).getImagePath()).into(viewHolder.myImageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.allImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, mData.get(getPosition()).getImageDate(), Toast.LENGTH_SHORT).show();
                    index = getPosition();
                    EditImageFragment myf = new EditImageFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.flAll, myf);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
    }
}
