package com.dadn.sgardens;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dadn.sgardens.network.ProductEntry;
import com.google.android.material.tabs.TabLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FloorFragment extends Fragment implements ProductCardRecyclerViewAdapter.OnItemClickListener{
    private String floorID;

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_TITLE = "TitleName";
    public static final String EXTRA_SUBTITLE = "Subtitle";


    private ProductCardRecyclerViewAdapter adapter;

    RequestQueue queue;
    GraphView graph;

    LineGraphSeries<DataPoint> mSeries = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> mSeries1 = new LineGraphSeries<DataPoint>();
    LineGraphSeries<DataPoint> mSeries2 = new LineGraphSeries<DataPoint>();

    List<DataPoint> lsDataPoint = new ArrayList<>();

    public FloorFragment(String floorID) {
        this.floorID = floorID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.floor_fragment, container, false);

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        adapter = new ProductCardRecyclerViewAdapter(
                ProductEntry.initProductEntryList(getResources(), floorID),getActivity());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

        graph = view.findViewById(R.id.graph1);
        queue = Volley.newRequestQueue(getActivity());
        String url = "https://api.thingspeak.com/channels/1078923/field/1.json?results=100";
        String url1 = "https://api.thingspeak.com/channels/1078923/field/2.json?results=100";
        String url2 = "https://api.thingspeak.com/channels/1078923/field/6.json?results=100";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int lastIndex = 0;
                    JSONArray jArr = response.getJSONArray("feeds");
                    Log.e("AAAAAAAAAAAAAAAAAAA", jArr.toString());
                    for(int i = 0; i < jArr.length(); i++){
                        JSONObject jo = jArr.getJSONObject(i);
                        if(!jo.getString("field1").equals("null")){
                            DataPoint data = new DataPoint(lastIndex, Double.parseDouble(jo.getString("field1")));
                            mSeries.appendData(data, true, 10000);
                            lsDataPoint.add(data);
                            lastIndex += 1;

                        }
                    }
                    mSeries.setColor(Color.GREEN);
                    drawGraph(lsDataPoint, response.getJSONObject("channel").getString("field1"));
                    graph.addSeries(mSeries);
                    Log.e("CCCCCCCCC", "END");
                }catch (Exception e){
                    Log.e("DDDDDDDDDDDDDDDDDD", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("BBBBBBBB", "BBBBBBBBBBBBBBBBBBBBBBBB");
            }
        });

        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int lastIndex1 = 0;
                    JSONArray jArr = response.getJSONArray("feeds");
                    Log.e("AAAAAAAAAAAAAAAAAAA", jArr.toString());
                    for(int i = 0; i < jArr.length(); i++){
                        JSONObject jo = jArr.getJSONObject(i);
                        if(!jo.getString("field2").equals("null")){
                            DataPoint data = new DataPoint(lastIndex1, Double.parseDouble(jo.getString("field2")));
                            mSeries1.appendData(data, true, 10000);
                            lastIndex1 += 1;
                        }
                    }
                    //drawGraph1(lsDataPoint, response.getJSONObject("channel").getString("field2"));
                    graph.addSeries(mSeries1);
                    Log.e("CCCCCCCCC", "END");
                }catch (Exception e){
                    Log.e("DDDDDDDDDDDDDDDDDD", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("BBBBBBBB", "BBBBBBBBBBBBBBBBBBBBBBBB");
            }
        });
        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int lastIndex1 = 0;
                    JSONArray jArr = response.getJSONArray("feeds");
                    Log.e("AAAAAAAAAAAAAAAAAAA", jArr.toString());
                    for(int i = 0; i < jArr.length(); i++){
                        JSONObject jo = jArr.getJSONObject(i);
                        if(!jo.getString("field6").equals("null")){
                            DataPoint data = new DataPoint(lastIndex1, Double.parseDouble(jo.getString("field6")));
                            mSeries2.appendData(data, true, 10000);
                            lastIndex1 += 1;
                        }
                    }
                    mSeries2.setColor(Color.RED);
                    //drawGraph1(lsDataPoint, response.getJSONObject("channel").getString("field2"));
                    graph.addSeries(mSeries2);
                    Log.e("CCCCCCCCC", "END");
                }catch (Exception e){
                    Log.e("DDDDDDDDDDDDDDDDDD", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("BBBBBBBB", "BBBBBBBBBBBBBBBBBBBBBBBB");
            }
        });

        queue.add(request);
        queue.add(request1);
        queue.add(request2);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(getActivity().getBaseContext(), DetailActivity.class);
        ProductEntry product = adapter.getProductList().get(position);

        detailIntent.putExtra(EXTRA_URL, product.url);
        detailIntent.putExtra(EXTRA_TITLE, product.title);
        detailIntent.putExtra(EXTRA_SUBTITLE, product.descriptions);

        startActivity(detailIntent);
    }

    void drawGraph(List<DataPoint> listData, String title){
        mSeries.setDrawDataPoints(true);
        mSeries.setDataPointsRadius(5);
        mSeries.setThickness(4);
        graph.addSeries(mSeries);
        graph.setTitle("biểu đồ ");
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(listData.size() - 10);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(20);
        graph.getViewport().setMaxY(100);
        graph.getViewport().setScrollable(true);
        graph.setTitleColor(Color.RED);
    }
}
