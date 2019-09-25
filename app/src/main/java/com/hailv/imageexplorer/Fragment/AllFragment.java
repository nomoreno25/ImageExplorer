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
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class AllFragment extends Fragment{
    public static ArrayList<ImageDataModel> allImages = new ArrayList<ImageDataModel>();
    private ArrayList<String> resultIAV = new ArrayList<String>();
    private ArrayList<String> fileList = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);

//        gettAllImages(getContext());
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView_all);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        getFile(root);

//        getFilePaths();
        for (int i = 0; i < fileList.size(); i++) {
            String stringFile = fileList.get(i);
            Log.e("TEST", stringFile);
        }

        AllAdapter mAdapter = new AllAdapter(getContext(), fileList);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public static ArrayList<ImageDataModel> gettAllImages(Context activity) {

        //Remove older images to avoid copying same image twice

        allImages.clear();
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null, imageName;

        //get all images from external storage

        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data);

            imageName = cursor.getString(column_index_folder_name);

            allImages.add(new ImageDataModel(imageName, absolutePathOfImage));

        }

        // Get all Internal storage images

        uri = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data);

            imageName = cursor.getString(column_index_folder_name);

            allImages.add(new ImageDataModel(imageName, absolutePathOfImage));
        }

        return allImages;
    }

    public ArrayList<String> getFilePaths() {
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<String>();
//        ArrayList<String> resultIAV = new ArrayList<String>();

        String[] directories = null;
        if (u != null)
        {
            c = getContext().getContentResolver().query(u, projection, null, null, null);
        }
        if ((c != null) && (c.moveToFirst()))
        {
            do
            {
                String tempDir = c.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try{
                    dirList.add(tempDir);
                }
                catch(Exception e)
                {
                }
            }
            while (c.moveToNext());
            directories = new String[dirList.size()];
            dirList.toArray(directories);
        }
        for(int i=0;i<dirList.size();i++)
        {
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if(imageList == null)
                continue;
            for (File imagePath : imageList) {
                try {

                    if(imagePath.isDirectory())
                    {
                        imageList = imagePath.listFiles();
                    }
                    if ( imagePath.getName().endsWith(".png")|| imagePath.getName().endsWith(".PNG")
                            || imagePath.getName().endsWith(".jpg")|| imagePath.getName().endsWith(".JPG")
                            || imagePath.getName().endsWith(".jpeg") || imagePath.getName().endsWith(".JPEG")
                            || imagePath.getName().endsWith(".gif") || imagePath.getName().endsWith(".GIF")
                            || imagePath.getName().endsWith(".bmp") || imagePath.getName().endsWith(".BMP")
                            || imagePath.getName().endsWith(".webp") || imagePath.getName().endsWith(".WEBP")
                    )
                    {
                        String path= imagePath.getAbsolutePath();
                        resultIAV.add(path);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resultIAV;
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
                        String temp = file.getPath();
                        if (!fileList.contains(temp))
                            fileList.add(temp);
                    }
                }
            }
        }
        return fileList;
    }
}
