package com.hailv.imageexplorer;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.hailv.imageexplorer.Adapter.FolderImageAdapter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.security.AccessController.getContext;

public class FolderImageActivity extends AppCompatActivity {
    private TextView folderPath;
    private String fPath;
    private ArrayList<String> resultIAV = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_image);
        folderPath = findViewById(R.id.fTxtView);
        fPath = getIntent().getStringExtra("fPath");
        folderPath.setText(fPath);

        RecyclerView mRecyclerView = findViewById(R.id.fRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        File dir = new File(fPath);
        getImages(dir);
        for (int i = 0; i < resultIAV.size(); i++) {
            String stringFile = resultIAV.get(i);
            Log.e("IMAGES", stringFile);
        }

        FolderImageAdapter mAdapter = new FolderImageAdapter(this, resultIAV);
        mRecyclerView.setAdapter(mAdapter);
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

//    public ArrayList<String> getImages() {
//        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        String[] projection = {MediaStore.Images.ImageColumns.DATA};
//        Cursor c = null;
//        SortedSet<String> dirList = new TreeSet<String>();
////        ArrayList<String> resultIAV = new ArrayList<String>();
//
//        String[] directories = null;
//        if (u != null)
//        {
//            c = getContentResolver().query(u, projection, null, null, null);
//        }
//        if ((c != null) && (c.moveToFirst()))
//        {
//            do
//            {
//                String tempDir = c.getString(0);
//                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
//                try{
//                    dirList.add(tempDir);
//                }
//                catch(Exception e)
//                {
//                }
//            }
//            while (c.moveToNext());
//            directories = new String[dirList.size()];
//            dirList.toArray(directories);
//        }
//        for(int i=0;i<dirList.size();i++)
//        {
//            File imageDir = new File(directories[i]);
//            File[] imageList = imageDir.listFiles();
//            if(imageList == null)
//                continue;
//            for (File imagePath : imageList) {
//                try {
//
//                    if(imagePath.isDirectory())
//                    {
//                        imageList = imagePath.listFiles();
//                    }
//                    if ( imagePath.getName().contains(".png")|| imagePath.getName().contains(".PNG")
//                            || imagePath.getName().contains(".jpg")|| imagePath.getName().contains(".JPG")
//                            || imagePath.getName().contains(".jpeg") || imagePath.getName().contains(".JPEG")
//                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
//                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
//                            || imagePath.getName().contains(".webp") || imagePath.getName().contains(".WEBP")
//                    )
//                    {
//                        String path= imagePath.getAbsolutePath();
//                        resultIAV.add(path);
//                    }
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return resultIAV;
//    }
}
