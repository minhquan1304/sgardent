package com.dadn.sgardens;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.dadn.sgardens.FloorFragment.EXTRA_SUBTITLE;
import static com.dadn.sgardens.FloorFragment.EXTRA_TITLE;
import static com.dadn.sgardens.FloorFragment.EXTRA_URL;


public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String imageUrl;
    private String productName;
    private String subTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        initActivity();

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(productName);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initActivity() {
        Intent intent = getIntent();
        imageUrl = intent.getStringExtra(EXTRA_URL);
        productName = intent.getStringExtra(EXTRA_TITLE);
        subTitle = intent.getStringExtra(EXTRA_SUBTITLE);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView productNameView = findViewById(R.id.text_view_creator_detail);
        TextView textViewLikes = findViewById(R.id.text_view_like_detail);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        productNameView.setText(productName);
        textViewLikes.setText(subTitle);





    }

//    void drawGraph(List<DataPoint> listData, String title){
//        mSeries.setDrawDataPoints(true);
//        mSeries.setDataPointsRadius(5);
//        mSeries.setThickness(4);
//        graph.addSeries(mSeries);
//        graph.setTitle("biểu đồ ");
//        graph.getViewport().setXAxisBoundsManual(true);
//        graph.getViewport().setMinX(0);
//        graph.getViewport().setMaxX(listData.size() - 10);
//        graph.getViewport().setYAxisBoundsManual(true);
//        graph.getViewport().setMinY(20);
//        graph.getViewport().setMaxY(100);
//        graph.getViewport().setScrollable(true);
//        graph.setTitleColor(Color.RED);
//    }


}
