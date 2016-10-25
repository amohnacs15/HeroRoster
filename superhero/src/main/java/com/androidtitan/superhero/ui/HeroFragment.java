package com.androidtitan.superhero.ui;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidtitan.superhero.R;
import com.androidtitan.superhero.adapter.HeroRecyclerViewAdapter;
import com.androidtitan.superhero.model.Heroes;
import com.androidtitan.superhero.model.MarvelResponse;
import com.androidtitan.superhero.retrofit.RetrofitEndpoint;
import com.androidtitan.superhero.retrofit.RetrofitService;
import com.androidtitan.superhero.model.Hero;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androidtitan.superhero.RequestHelper.md5;

public class HeroFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    private static final String ARG_COLUMN_COUNT = "column-count";

    RetrofitEndpoint retrofitClient;
    HeroRecyclerViewAdapter adapter;

    List<Hero> heroList;
    private int mColumnCount = 2;

    public HeroFragment() {
    }


    public static HeroFragment newInstance(int columnCount) {
        HeroFragment fragment = new HeroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hero_list, container, false);

        retrofitClient = RetrofitService.createService(RetrofitEndpoint.class);
        heroList = new ArrayList<>();

        adapter = new HeroRecyclerViewAdapter(getActivity(), getHeroList());

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(mColumnCount, 1));
            }
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private List<Hero> getHeroList() {

        Call<MarvelResponse> call = retrofitClient.requestHeroList("-modified", 20, System.currentTimeMillis(),
                getContext().getResources().getString(R.string.api_kay), hash());

        call.enqueue(new Callback<MarvelResponse>() {
            @Override
            public void onResponse(Call<MarvelResponse> call, Response<MarvelResponse> response) {

                try {
                    Log.e(TAG, "Sent a request to: " + call.request().toString());
                    heroList.addAll(response.body().getData().getResults());
                    adapter.notifyDataSetChanged();
                } catch (NullPointerException e) {
                    getHeroList();
                }
            }

            @Override
            public void onFailure(Call<MarvelResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return heroList;
    }

    private String hash() {

        long timeStamp = System.currentTimeMillis();
        String publicKey = getContext().getResources().getString(R.string.api_kay);
        String privateKey = getContext().getResources().getString(R.string.private_key);

        String stringToConvert = timeStamp + privateKey + publicKey;
        String hash = md5(stringToConvert);

        return hash;

    }

}
