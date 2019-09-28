package com.hailv.imageexplorer.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hailv.imageexplorer.Adapter.FolderAdapter;
import com.hailv.imageexplorer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class FolderFragment extends Fragment {
    public static ArrayList<String> fileList = new ArrayList<String>();
    private Context context;

    public FolderFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder, container, false);

        //Set layout recyclerview
        ListView mRecyclerView = view.findViewById(R.id.listView_folder);
        //Lay danh sach folder path
        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        getFile(root);
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(fileList);
        fileList.clear();
        fileList.addAll(hashSet);
        Collections.sort(fileList);
        //Add vao adapter va hien thi
        FolderAdapter mAdapter = new FolderAdapter(getContext(), fileList);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public ArrayList<String> getFile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (File file : listFile) {
                if (file.isDirectory()) {
                    getFile(file);
                }
                else {
                    if (file.getName().endsWith(".png")
                            || file.getName().endsWith(".jpg")
                            || file.getName().endsWith(".jpeg")
                            || file.getName().endsWith(".gif")
                            || file.getName().endsWith(".bmp")
                            || file.getName().endsWith(".webp")
                    )
                    {
                        String temp = file.getPath().substring(0, file.getPath().lastIndexOf('/'));
                        if (!fileList.contains(temp))
                            fileList.add(temp);
                    }
                }
            }
        }
        return fileList;
    }
}