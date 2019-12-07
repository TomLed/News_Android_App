/* Ce qui est en commentaire permet d'adapter les articles à la localisation du téléphone. Dans mon
cas il met US par défaut, j'ai donc définit manuellement la variable country sur la valeur "fr"
 */
package com.example.yourdailydigest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.yourdailydigest.ApiCLient;
import com.example.yourdailydigest.Model.Articles;
import com.example.yourdailydigest.Model.Headlines;
import com.example.yourdailydigest.R;

import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    final String API_KEY = "d5e1f0d930974389b567801f96aa2bed";
    final String country = "fr";
    Adapter adapter;
    List<Articles> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson(country, API_KEY);
            }
        });

        //String country = getCountry();
        retrieveJson(country, API_KEY);

    }

    public void retrieveJson(String country, String apiKey){

        swipeRefreshLayout.setRefreshing(true);

        Call<Headlines> call = ApiCLient.getInstance().getApi().getHeadlines(country, apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null){
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(MainActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(true);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

   /* public String getCountry(){
       // Locale locale = Locale.getDefault();
        //String country = locale.getCountry();
        //return country.toLowerCase();
       // return "fr";
    } */
}
