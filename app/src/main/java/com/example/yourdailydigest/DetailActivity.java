package com.example.yourdailydigest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    WebView webView;
    ImageView imageView;
    TextView tvTitle, tvSource, tvDate, tvDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.imageView);
        webView = findViewById(R.id.webView);

        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvSource = findViewById(R.id.tvSource);
        tvDesc = findViewById(R.id.tvDesc);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String author = intent.getStringExtra("author");
        String date = intent.getStringExtra("date");
        String url = intent.getStringExtra("url");
        String imageSource = intent.getStringExtra("imageSource");
        String description = intent.getStringExtra("description");

        tvTitle.setText(title);
        tvDesc.setText(description);
        tvSource.setText(source);
        tvDate.setText(date);

        Picasso.with(DetailActivity.this).load(imageSource).into(imageView);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}
