package com.androidtitan.superhero.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtitan.superhero.R;
import com.androidtitan.superhero.model.Hero;
import com.androidtitan.superhero.ui.HeroDetailActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import static com.androidtitan.superhero.Constants.HERO_ID_EXTRA;


public class HeroRecyclerViewAdapter extends RecyclerView.Adapter<HeroRecyclerViewAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();

    private final Context context;
    private final List<Hero> heroes;

    public HeroRecyclerViewAdapter(Context context, List<Hero> items) {

        this.context = context;
        heroes = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String urlString = heroes.get(position).getThumbnail().getPath()
                + "/portrait_incredible." + heroes.get(position).getThumbnail().getExtension();


        holder.mItem = heroes.get(position);
        holder.itemName.setText(heroes.get(position).getName());


        Glide.with(context)
                .load(urlString)
                .crossFade()
                .into(holder.itemImage);


        holder.clickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HeroDetailActivity.class);

                intent.putExtra(HERO_ID_EXTRA, heroes.get(position).getId());

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
        public Hero mItem;


        public ViewHolder(View view) {
            super(view);

            clickLayout = (CardView) view.findViewById(R.id.placeCard);
            itemImage = (ImageView) view.findViewById(R.id.heroImageView);
            itemName = (TextView) view.findViewById(R.id.heroName);

        }

    }
}
