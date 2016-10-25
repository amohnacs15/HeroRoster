package com.androidtitan.simplehero.threads;

import com.androidtitan.simplehero.model.Hero;

import java.util.ArrayList;

/**
 * Created by amohnacs on 10/22/16.
 */
public interface AsyncTaskInterface {

    void updateAdapter(Hero hero);
    void updateAdapter(ArrayList<Hero> heros);
}
