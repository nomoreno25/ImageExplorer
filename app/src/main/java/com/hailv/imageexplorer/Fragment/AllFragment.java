package com.hailv.imageexplorer.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hailv.imageexplorer.Adapter.AllAdapter;
import com.hailv.imageexplorer.Modal.ImageDataModel;
import com.hailv.imageexplorer.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

public class AllFragment extends Fragment{
    public static ArrayList<ImageDataModel> allImages = new ArrayList<ImageDataModel>();
    public static ArrayList<ImageDataModel> fileList = new ArrayList<ImageDataModel>();
    File file;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);

//        gettAllImages(getContext());
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView_all);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        getFile(file);

//        getFilePaths();
        for (int i = 0; i < allImages.size(); i++) {
            ImageDataModel imageDataModel = allImages.get(i);
            fileList.add(new ImageDataModel(imageDataModel.getImagePath(), imageDataModel.getImageDate()));
        }
        Collections.sort(fileList, new StringDateComparator());
//        MainActivity.progressBar.setVisibility(View.GONE);
        AllAdapter mAdapter = new AllAdapter(getContext(), fileList);
        mRecyclerView.hasFixedSize();
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    class StringDateComparator implements Comparator<ImageDataModel> {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

        public int compare(ImageDataModel s1, ImageDataModel s2) {
            Date d1 = null;
            Date d2 = null;
            try {
                d1 = dateFormat.parse(s1.getImageDate());
                d2 = dateFormat.parse(s2.getImageDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (d1.getTime() == d2.getTime())
                return 0;
            else if (d1.getTime() < d2.getTime())
                return 1;
            else
                return -1;
        }
    }

    public void getFile(File dir) {

        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    getFile(listFile[i]);
                } else {
                    if (listFile[i].getName().endsWith(".png")
                            || listFile[i].getName().endsWith(".jpg")
                            || listFile[i].getName().endsWith(".jpeg")
                            || listFile[i].getName().endsWith(".gif")
                            || listFile[i].getName().endsWith(".bmp")) {
                        String temp = listFile[i].getPath();
                        if (!allImages.contains(temp)) {
                            File file = new File(temp);
                            Date lastModifiedDate = new Date(file.lastModified());
                            String format = "MM-dd-yyyy HH:mm:ss";
                            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
                            String dt = formatter.format(new Date(String.valueOf(lastModifiedDate)));
                            ;
                            allImages.add(new ImageDataModel(temp, dt));

                        }

                    }
                }
            }
        }
    }

//    public ArrayList<String> getFile(File dir) {
//        File listFile[] = dir.listFiles();
//        if (listFile != null && listFile.length > 0) {
//            for (File file : listFile) {
//                if (file.isDirectory()) {
//                    getFile(file);
//                }
//                else {
//                    if (file.getName().endsWith(".png")
//                            || file.getName().endsWith(".jpg")
//                            || file.getName().endsWith(".jpeg")
//                            || file.getName().endsWith(".gif")
//                            || file.getName().endsWith(".bmp")
//                            || file.getName().endsWith(".webp")
//                    )
//                    {
//                        String temp = file.getPath();
//                        if (!fileList.contains(temp))
//                            fileList.add(temp);
//                    }
//                }
//            }
//        }
//        return fileList;
//    }
}
