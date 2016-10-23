package com.androidtitan.simplehero.threads;

import com.androidtitan.simplehero.model.SuperHero;

import java.util.ArrayList;

/**
 * Created by amohnacs on 10/22/16.
 */
public interface AsyncTaskInterface {

    void updateAdapter(SuperHero hero);
    void updateAdapter(ArrayList<SuperHero> heros);
}
