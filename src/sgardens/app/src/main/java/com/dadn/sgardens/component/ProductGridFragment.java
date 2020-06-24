package com.dadn.sgardens.component;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dadn.sgardens.DetailActivity;
import com.dadn.sgardens.FloorFragment;
import com.dadn.sgardens.ProductCardRecyclerViewAdapter;
import com.dadn.sgardens.ProductGridItemDecoration;
import com.dadn.sgardens.R;
import com.dadn.sgardens.network.ProductEntry;
import com.google.android.material.tabs.TabLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductGridFragment extends Fragment  {
    TabLayout tabLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.product_grid_fragment, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        loadFragment(new FloorFragment("Floor1"));
                        //ft.commit();
                        Log.e("AAAAAAAAAAAAAAAAAAAA", "000000000000000000000000000");
                        break;
                    case 1:
                        loadFragment(new FloorFragment("Floor2"));
                        //ft.commit();
                        Log.e("BBBBBBBBBBBBBBBBBBBBB", "!1111111111111111111111111");
                        break;
                    default:
                        Log.e("ERROR","ERROR");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        loadFragment(new FloorFragment("Floor1"));
        return view;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.floor_fragment_holder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
