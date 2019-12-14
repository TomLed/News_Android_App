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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
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

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    EditText etQuery;
    Button  btSearch;
    Button btFilter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    final String API_KEY = "d5e1f0d930974389b567801f96aa2bed";
    final String country = "fr";
    Adapter adapter;
    List<Articles> articles = new ArrayList<>();
    String sources = "";

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all:
                Toast.makeText(this, "Source: A la Une", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        retrieveJson("", "",country,API_KEY);
                    }
                });
                retrieveJson("",etQuery.getText().toString(), country, API_KEY);
                return false;
            case R.id.GoogleNews:
                Toast.makeText(this, "Source: Google News", Toast.LENGTH_SHORT).show();
                sources ="google-news-fr";
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        retrieveJson("google-news-fr", "",country,API_KEY);
                    }
                });
                retrieveJson("google-news-fr","", country, API_KEY);
            case R.id.leMonde:
                Toast.makeText(this, "Source: Le Monde", Toast.LENGTH_SHORT).show();
                sources = "le-monde";
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        retrieveJson(sources, "",country,API_KEY);
                    }
                });
                retrieveJson(sources,etQuery.getText().toString(), country, API_KEY);
                return true;
            case R.id.liberation:
                Toast.makeText(this, "Source: Libération", Toast.LENGTH_SHORT).show();
                sources = "liberation";
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        retrieveJson(sources, "",country,API_KEY);
                    }
                });
                retrieveJson(sources,etQuery.getText().toString(), country, API_KEY);
                return true;
            case R.id.echos:
                Toast.makeText(this, "Source: Les Echos", Toast.LENGTH_SHORT).show();
                sources = "les-echos";
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        retrieveJson(sources, "",country,API_KEY);
                    }
                });
                retrieveJson(sources,etQuery.getText().toString(), country, API_KEY);
                return true;
            case R.id.equipe:
                Toast.makeText(this, "Source: L'Equipe", Toast.LENGTH_SHORT).show();
                sources = "lequipe";
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        retrieveJson(sources, "",country,API_KEY);
                    }
                });
                retrieveJson(sources,etQuery.getText().toString(), country, API_KEY);
                return true;
            default:
                return false;
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);

        etQuery = findViewById(R.id.etQuery);
        btSearch = findViewById(R.id.btSearch);
        btFilter = findViewById(R.id.btFilter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("","", country, API_KEY);
            }
        });

        //String country = getCountry();
        retrieveJson("", "",country, API_KEY);


        btFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                popup.setOnMenuItemClickListener(MainActivity.this);
                popup.inflate(R.menu.popupmenu);
                popup.show();

            }
        });



        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etQuery.getText().toString().equals("")){
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson("", etQuery.getText().toString(),country,API_KEY);
                        }
                    });
                    retrieveJson("",etQuery.getText().toString(), country, API_KEY);
                }else{
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson("", "",country,API_KEY);
                        }
                    });
                    retrieveJson("", "",country,API_KEY);
                }

            }
        });
    }

    public void retrieveJson(String sources, String query, String country, String apiKey){

        swipeRefreshLayout.setRefreshing(true);

        Call<Headlines> call;
        if (!etQuery.getText().toString().equals("")){
            call = ApiCLient.getInstance().getApi().getSpecific(query,apiKey);
        }else{
            if (!sources.equals("")) {
                call = ApiCLient.getInstance().getApi().getSource(sources, apiKey);
            }
            else{
                call = ApiCLient.getInstance().getApi().getHeadlines(country,apiKey);
            }
        }
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
