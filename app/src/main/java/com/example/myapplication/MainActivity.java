package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.adapter.AllMenuAdapter;
import com.example.myapplication.adapter.PopularAdapter;
import com.example.myapplication.adapter.RecommendedAdapter;
import com.example.myapplication.model.Allmenu;
import com.example.myapplication.model.FoodData;
import com.example.myapplication.model.Popular;
import com.example.myapplication.model.Recommended;
import com.example.myapplication.retrofit.ApiInterface;
import com.example.myapplication.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    HomeFragment homefragment = new HomeFragment();
    KeranjangFragment cartfragment = new KeranjangFragment();
    AkunFragment akunfragment = new AkunFragment();


    ApiInterface apiInterface;

    RecyclerView popularRecyclerView, recommendedRecyclerView, allMenuRecyclerView;

    PopularAdapter popularAdapter;
    RecommendedAdapter recommendedAdapter;
    AllMenuAdapter allMenuAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homefragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homefragment).commit();
                        return true;
                    case R.id.cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,cartfragment).commit();
                        return true;
                    case R.id.akun:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,akunfragment).commit();
                        return true;
                }

                return false;
            }
        });


        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<List<FoodData>> call = apiInterface.getAllData();
        call.enqueue(new Callback<List<FoodData>>() {
            @Override
            public void onResponse(Call<List<FoodData>> call, Response<List<FoodData>> response) {

                List<FoodData> foodDataList = response.body();


                getPopularData(foodDataList.get(0).getPopular());

                getRecommendedData(foodDataList.get(0).getRecommended());

                getAllMenu(foodDataList.get(0).getAllmenu());
                // lets run it.
                // we have fetched data from server.
                // now we have to show data in app using recycler view
                // lets make recycler view adapter
                // we have setup and bind popular section
                // in a same way we add recommended and all menu items
                // we add two adapter class for allmenu and recommended items.
                // so lets do it fast.

            }

            @Override
            public void onFailure(Call<List<FoodData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void  getPopularData(List<Popular> popularList){

        popularRecyclerView = findViewById(R.id.popular_recycler);
        popularAdapter = new PopularAdapter(this, popularList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerView.setAdapter(popularAdapter);

    }

    private void  getRecommendedData(List<Recommended> recommendedList){

        recommendedRecyclerView = findViewById(R.id.recommended_recycler);
        recommendedAdapter = new RecommendedAdapter(this, recommendedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

    }

    private void  getAllMenu(List<Allmenu> allmenuList){

        allMenuRecyclerView = findViewById(R.id.all_menu_recycler);
        allMenuAdapter = new AllMenuAdapter(this, allmenuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(allMenuAdapter);
        allMenuAdapter.notifyDataSetChanged();


    }
}