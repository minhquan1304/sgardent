package com.dadn.sgardens.network;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;


import com.dadn.sgardens.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A product entry in the list of products.
 */
public class ProductEntry {
    private static final String TAG = ProductEntry.class.getSimpleName();

    public final String title;
    public final Uri dynamicUrl;
    public final String url;
    public final String descriptions;
    public final String description;
    public String floorID;

    public ProductEntry(
            String title, String dynamicUrl, String url, String price, String description, String floorID) {
        this.title = title;
        this.dynamicUrl = Uri.parse(dynamicUrl);
        this.url = url;
        this.description = price;
        this.descriptions = description;
        this.floorID = floorID;
    }

    /**
     * Loads a raw JSON at R.raw.products and converts it into a list of ProductEntry objects
     */
    public static List<ProductEntry> initProductEntryList(Resources resources, String floorID) {
        InputStream inputStream = resources.openRawResource(R.raw.products);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }
        String jsonProductsString = writer.toString();
        List<ProductEntry> products = new ArrayList<>();
        JSONArray jsonArr;
        try {
            jsonArr = new JSONArray(jsonProductsString);
            for(int i=0; i<jsonArr.length(); i++){
                JSONObject jo = jsonArr.getJSONObject(i);
                if(jo.getString("floor").equals(floorID)){
                    products.add(new ProductEntry(jo.getString("title"),
                            jo.getString("url"),
                            jo.getString("url"),
                            null,
                            jo.getString("descriptions"),
                            jo.getString("floor")
                            ));
                }
            }
        }catch(Exception e){

        }
        return products;
    }

    @Override
    public String toString() {
        return "ProductEntry{" +
                "title='" + title + '\n' +
                ", url='" + url + '\n' +
                ", descriptions='" + descriptions + '\n' +
                ", description='" + description + '\n' +
                '}';
    }
}