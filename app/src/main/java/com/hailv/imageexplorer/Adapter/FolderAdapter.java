package com.hailv.imageexplorer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hailv.imageexplorer.FolderImageActivity;
import com.hailv.imageexplorer.Modal.ImageDataModel;
import com.hailv.imageexplorer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {
    private ArrayList<String> listThumuc = new ArrayList<String>();
    private ArrayList<String> mData;
    private LayoutInflater mInflater;
    private Context context;

    public FolderAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public FolderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_folder,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderAdapter.ViewHolder viewHolder, int i) {
        for (int x = 0; x < mData.size(); x++) {
            String thumucpath = mData.get(x).substring(mData.get(x).lastIndexOf("/")+1);
            listThumuc.add(thumucpath);
        }
        //Loai bo trung lap
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(listThumuc);
        listThumuc.clear();
        listThumuc.addAll(hashSet);
        Collections.sort(listThumuc);
        //Hien thi len RecyclerView
        viewHolder.myTextView.setText(listThumuc.get(i));
        //Click item RecyclerView
        final String fPath = mData.get(i);
        viewHolder.myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, FolderImageActivity.class).putExtra("fPath",fPath));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.folderTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}