package com.hailv.imageexplorer.Adapter;

import android.content.Context;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hailv.imageexplorer.Fragment.FolderImageFragment;
import com.hailv.imageexplorer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class FolderAdapter extends BaseAdapter {
    private ArrayList<String> listThumuc = new ArrayList<String>();
    private ArrayList<String> mData;
    private LayoutInflater mInflater;
    private Context context;
    public static int index=0;

    public FolderAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_folder,null);
            viewHolder.folderImageView = view.findViewById(R.id.folderImageView);
            viewHolder.folderTextView = view.findViewById(R.id.folderTextView);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
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
        viewHolder.folderTextView.setText(listThumuc.get(i));

        final String fPath = mData.get(i);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = i;
                FolderImageFragment myf = new FolderImageFragment();
                FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flFolder, myf);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    public class ViewHolder {
        ImageView folderImageView;
        TextView folderTextView;
    }
}