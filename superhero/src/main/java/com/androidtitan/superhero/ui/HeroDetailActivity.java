package com.androidtitan.superhero.ui;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.androidtitan.superhero.R;
import com.androidtitan.superhero.model.UrlItem;
import com.androidtitan.superhero.model.Hero;
import com.androidtitan.superhero.model.MarvelResponse;
import com.androidtitan.superhero.retrofit.RetrofitEndpoint;
import com.androidtitan.superhero.retrofit.RetrofitService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.flaviofaria.kenburnsview.KenBurnsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androidtitan.superhero.Constants.*;
import static com.androidtitan.superhero.RequestHelper.md5;

public class HeroDetailActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private RetrofitEndpoint retrofitClient;

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    private KenBurnsView imageView;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView comicsTextView;

    private Hero hero;

    private int heroId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        retrofitClient = RetrofitService.createService(RetrofitEndpoint.class);

        Intent receivedIntent = getIntent();
        if(receivedIntent.getExtras() != null) {
            heroId = receivedIntent.getIntExtra(HERO_ID_EXTRA, -1);
        }

        imageView = (KenBurnsView) findViewById(R.id.heroDetailImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        comicsTextView = (TextView) findViewById(R.id.comicsTextView);

        getSupportActionBar().setTitle("");
        loadHero();


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

    private void loadHero() {

        Call<MarvelResponse> call = retrofitClient.requestHero(heroId, System.currentTimeMillis(),
                getResources().getString(R.string.api_kay), hash());

        call.enqueue(new Callback<MarvelResponse>() {
            @Override
            public void onResponse(Call<MarvelResponse> call, Response<MarvelResponse> response) {

                try {
                    Log.e(TAG, "Sent a request to: " + call.request().toString());

                    hero = response.body().getData().getResults().get(0);
                    loadHeroUI(hero);

                } catch (NullPointerException e) {
                    loadHero();
                }
            }

            @Override
            public void onFailure(Call<MarvelResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private String hash() {

        long timeStamp = System.currentTimeMillis();
        String publicKey = getResources().getString(R.string.api_kay);
        String privateKey = getResources().getString(R.string.private_key);

        String stringToConvert = timeStamp + privateKey + publicKey;
        String hash = md5(stringToConvert);

        return hash;

    }

    private void loadHeroUI (Hero hero) {
        String urlString = hero.getThumbnail().getPath()
                + "/landscape_incredible." + hero.getThumbnail().getExtension();

        StringBuilder builder = new StringBuilder();
        for(UrlItem comic : hero.getComics().getItems()) {
            builder.append(comic.getName() + "\n");
        }

        nameTextView.setText(hero.getName());
        descriptionTextView.setText(hero.getDescription().equals("")?
                "No description available. They must not be that interesting" : hero.getDescription());
        comicsTextView.setText(builder.toString());


        Glide.with(this)
                .load(urlString)
                .asBitmap()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);

                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                // Here's your generated palette

                                int bgColor = palette.from(resource).generate().getLightVibrantColor(
                                        ContextCompat.getColor(HeroDetailActivity.this, R.color.colorAccent));

                                nameTextView.setBackgroundColor(bgColor);
                                setToolbarColor(bgColor);

                            }
                        });
                    }
                });
    }

    private void setToolbarColor(final int bgColor) {
        //Set a listener to know the current visible state of CollapseLayout
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, int verticalOffset) {
                //Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                //Check if the view is collapsed
                if (scrollRange + verticalOffset == 0) {
                    toolbar.setBackgroundColor(bgColor);
                    getSupportActionBar().setElevation(0);
                }else{
                    toolbar.setBackgroundColor(ContextCompat.getColor(HeroDetailActivity.this, android.R.color.transparent));
                }
            }
        });
    }
}
