package com.androidtitan.simplehero.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtitan.simplehero.R;
import com.androidtitan.simplehero.threads.ImageDownloader;

import static com.androidtitan.simplehero.Constants.HERO_DESCRIPTION_EXTRA;
import static com.androidtitan.simplehero.Constants.HERO_IMAGE_EXTENSION_EXTRA;
import static com.androidtitan.simplehero.Constants.HERO_IMAGE_URL_EXTRA;
import static com.androidtitan.simplehero.Constants.HERO_NAME_EXTRA;

public class HeroDetailActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private ImageView imageView;
    private TextView descriptionTextView;

    private String heroName;
    private String heroDescription;
    private String heroImageUrl;
    private String heroImageExtension;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent receivedIntent = getIntent();
        if(receivedIntent.getExtras() != null) {
            heroName = receivedIntent.getStringExtra(HERO_NAME_EXTRA);
            heroDescription = receivedIntent.getStringExtra(HERO_DESCRIPTION_EXTRA);
            heroImageUrl = receivedIntent.getStringExtra(HERO_IMAGE_URL_EXTRA);
            heroImageExtension = receivedIntent.getStringExtra(HERO_IMAGE_EXTENSION_EXTRA);
        }

        imageView = (ImageView) findViewById(R.id.heroDetailImageView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);

        getSupportActionBar().setTitle(heroName);
        descriptionTextView.setText(heroDescription.equals("")?
                "No description available. They must not be that interesting" : heroDescription);

        String uriString = heroImageUrl
                + "/landscape_incredible." + heroImageExtension;
        try {
            ImageDownloader imageDownloader = new ImageDownloader();
            imageDownloader.imageDownload(uriString, imageView);
        } catch(Exception e) {
            e.printStackTrace();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
