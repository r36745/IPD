package com.rosemak.dogcentralv106;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by stevierose on 12/6/14.
 */
public final class UIHelper {
    ConnectivityManager manager;

    public UIHelper() {}

    public  boolean isNetworkAvailable(Activity main) {

        manager = (ConnectivityManager) main.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
            return isAvailable;
        }
        return isAvailable;
    }

    public void writeToFile(Activity main, String _fileName, String _weatherDescription, String _weatherType) {
       // MainActivity mainActivity = new MainActivity();
        File external = main.getExternalFilesDir(null);
        File file = new File(external, _fileName);

        try {
            FileOutputStream outPutStream = new FileOutputStream(file);
            outPutStream.write(_weatherDescription.getBytes());
            outPutStream.write(_weatherType.getBytes());
            outPutStream.close();
        } catch (FileNotFoundException e) {
           // Log.e(TAG, "File Not Found", e);
        } catch (IOException e) {
           // Log.e(TAG, "IOException= ", e);
        }
    }

    public String readFromFile(Activity main, String _fileName) {
       // MainActivity mainActivity = new MainActivity();
        File external = main.getExternalFilesDir(null);
        File file = new File(external, _fileName);
        if (!file.exists()) {
            return null;
        }

        try {
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inPutReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inPutReader);

            StringBuffer buffer = new StringBuffer();
            String bufferString = null;
            while ((bufferString = bufferedReader.readLine()) !=null ){
                buffer.append(bufferString +"\n");
            }
            bufferedReader.close();
            return buffer.toString().trim();
        } catch (FileNotFoundException e) {
           // Log.e(TAG, "Input File Not Found", e);
        } catch (IOException e) {
          //  Log.e(TAG, "Input IOException= ", e);
        }
        return null;
    }


}
