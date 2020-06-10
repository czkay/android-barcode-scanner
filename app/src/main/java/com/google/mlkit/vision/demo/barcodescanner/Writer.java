package com.google.mlkit.vision.demo.barcodescanner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.thegrizzlylabs.sardineandroid.Sardine;
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;

public class Writer {
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final Sardine SARDINE = new OkHttpSardine();
    private static final String FILE_PATH = "test_path";
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static byte[] getByteArrayFromFile(String filePath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            for (int readNum; (readNum = fis.read(b)) != -1; ) {
                bos.write(b, 0, readNum);
            }
            return bos.toByteArray();
        } catch (Exception e) {
            Log.e("WRITE", e.toString());
        }
        return null;
    }

    public static void writeBarcodeValue(String value) {
        Log.i("WRITE", String.format("Writing %s to local text file...", value));
        try {
            // -------------------------- write to local ---------------------------
            File f = new File(android.os.Environment.getExternalStorageDirectory(),"test.txt");
            FileWriter fw = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(value);
            out.flush();
            Log.i("WRITE", String.format("Successfully wrote NRIC to text file at %s.", f.getAbsolutePath()));
            // ------------------------ write via WebDav ---------------------------
//            Log.i("WRITE", String.format("Writing %s to remote text file via WebDav...", value));
//            byte[] data = getByteArrayFromFile(f.getAbsolutePath());
//            SARDINE.put("http://yourdavserver.com/adirectory/nameOfFile.jpg", data);

        } catch (IOException e) {
            Log.e("WRITE", e.toString());
        }
    }
}
