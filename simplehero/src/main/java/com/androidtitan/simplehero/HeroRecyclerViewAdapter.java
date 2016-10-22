package com.androidtitan.simplehero;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtitan.simplehero.model.SuperHero;

import java.util.ArrayList;
import java.util.List;


public class HeroRecyclerViewAdapter extends RecyclerView.Adapter<HeroRecyclerViewAdapter.ViewHolder> {

    private final List<SuperHero> mValues;

    public HeroRecyclerViewAdapter(List<SuperHero> items) {

        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String uriString = mValues.get(position).getImageUrl()
                + "/portrait_incredible." + mValues.get(position).getImageExtension();


        holder.mItem = mValues.get(position);
        holder.itemName.setText(mValues.get(position).getName());

        try {
            ImageDownloader imageDownloader = new ImageDownloader();
            imageDownloader.imageDownload(uriString,
                    holder.itemImage);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(mValues != null) {
            return mValues.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView itemImage;
        public final TextView itemName;
        public SuperHero mItem;

        public ViewHolder(View view) {
            super(view);
            itemImage = (ImageView) view.findViewById(R.id.heroImageView);
            itemName = (TextView) view.findViewById(R.id.heroName);
        }

    }
}
