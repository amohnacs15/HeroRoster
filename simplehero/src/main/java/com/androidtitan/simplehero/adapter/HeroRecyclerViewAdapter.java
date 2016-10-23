package com.androidtitan.simplehero.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtitan.simplehero.Constants;
import com.androidtitan.simplehero.R;
import com.androidtitan.simplehero.model.SuperHero;
import com.androidtitan.simplehero.threads.ImageDownloader;
import com.androidtitan.simplehero.ui.HeroDetailActivity;

import java.util.List;

import static com.androidtitan.simplehero.Constants.HERO_DESCRIPTION_EXTRA;
import static com.androidtitan.simplehero.Constants.HERO_IMAGE_EXTENSION_EXTRA;
import static com.androidtitan.simplehero.Constants.HERO_IMAGE_URL_EXTRA;
import static com.androidtitan.simplehero.Constants.HERO_NAME_EXTRA;


public class HeroRecyclerViewAdapter extends RecyclerView.Adapter<HeroRecyclerViewAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();

    private final Context context;
    private final List<SuperHero> heroes;

    public HeroRecyclerViewAdapter(Context context, List<SuperHero> items) {

        this.context = context;
        heroes = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String uriString = heroes.get(position).getImageUrl()
                + "/portrait_incredible." + heroes.get(position).getImageExtension();


        holder.mItem = heroes.get(position);
        holder.itemName.setText(heroes.get(position).getName());

        try {
            ImageDownloader imageDownloader = new ImageDownloader();
            imageDownloader.imageDownload(uriString,
                    holder.itemImage);
        } catch(Exception e) {
            e.printStackTrace();
        }

        holder.clickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HeroDetailActivity.class);

                intent.putExtra(HERO_NAME_EXTRA, heroes.get(position).getName());
                intent.putExtra(HERO_DESCRIPTION_EXTRA, heroes.get(position).getDescription());
                intent.putExtra(HERO_IMAGE_URL_EXTRA, heroes.get(position).getImageUrl());
                intent.putExtra(HERO_IMAGE_EXTENSION_EXTRA, heroes.get(position).getImageExtension());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(heroes != null) {
            return heroes.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final CardView clickLayout;
        public final ImageView itemImage;
        public final TextView itemName;
        public SuperHero mItem;

        public ViewHolder(View view) {
            super(view);

            clickLayout = (CardView) view.findViewById(R.id.placeCard);
            itemImage = (ImageView) view.findViewById(R.id.heroImageView);
            itemName = (TextView) view.findViewById(R.id.heroName);
        }

    }
}
