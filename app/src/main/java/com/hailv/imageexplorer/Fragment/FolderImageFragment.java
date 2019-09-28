package com.hailv.imageexplorer.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hailv.imageexplorer.Adapter.FolderAdapter;
import com.hailv.imageexplorer.Adapter.FolderImageAdapter;
import com.hailv.imageexplorer.R;

import java.io.File;
import java.util.ArrayList;

public class FolderImageFragment extends Fragment {
    private String fPath;
    private ArrayList<String> resultIAV = new ArrayList<String>();

    public FolderImageFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder_image, container, false);
        fPath = FolderFragment.fileList.get(FolderAdapter.index);

        RecyclerView mRecyclerView = view.findViewById(R.id.fRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        File dir = new File(fPath);
        getImages(dir);
        for (int i = 0; i < resultIAV.size(); i++) {
            String stringFile = resultIAV.get(i);
            Log.e("IMAGES", stringFile);
        }

        FolderImageAdapter mAdapter = new FolderImageAdapter(getContext(), resultIAV);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public ArrayList<String> getImages(File dir){
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (File file : listFile) {
                if (file.isDirectory()) {
                    getImages(dir);
                }
                else {
                    if (file.getName().endsWith(".png")
                            || file.getName().endsWith(".jpg")
                            || file.getName().endsWith(".jpeg")
                            || file.getName().endsWith(".gif")
                            || file.getName().endsWith(".bmp")
                            || file.getName().endsWith(".webp"))
                    {
                        String temp = file.getPath();
                        if (!resultIAV.contains(temp))
                            resultIAV.add(temp);
                    }
                }
            }
        }
        return resultIAV;
    }
}
