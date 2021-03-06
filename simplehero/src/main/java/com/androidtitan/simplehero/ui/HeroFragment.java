package com.androidtitan.simplehero.ui;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidtitan.simplehero.threads.AsyncTaskInterface;
import com.androidtitan.simplehero.threads.HeroDownloadAsyncTask;
import com.androidtitan.simplehero.adapter.HeroRecyclerViewAdapter;
import com.androidtitan.simplehero.R;
import com.androidtitan.simplehero.model.Hero;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link AsyncTaskInterface}
 * interface.
 */
public class HeroFragment extends Fragment implements AsyncTaskInterface{
    private final String TAG = getClass().getSimpleName();

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;

    private HeroRecyclerViewAdapter adapter;

    private ArrayList<Hero> heroList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HeroFragment() {
    }

    @SuppressWarnings("unused")
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

        heroList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hero_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(mColumnCount, 1));
            }

            adapter = new HeroRecyclerViewAdapter(getActivity(), heroList);
            recyclerView.setAdapter(adapter);
            try {

                fetchSuperHeros();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    private void fetchSuperHeros() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String publicKey = getContext().getResources().getString(R.string.public_key);
        String privateKey = getContext().getResources().getString(R.string.private_key);

        new HeroDownloadAsyncTask(this).execute(privateKey, publicKey);

    }

    @Override
    public void updateAdapter(Hero hero) {

        heroList.add(hero);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateAdapter(ArrayList<Hero> heroes) {

        heroList.clear();
        heroList.addAll(heroes);
        adapter.notifyDataSetChanged();
    }
}
