package com.androidtitan.superhero.model;

import java.util.List;

/**
 * Created by amohnacs on 10/23/16.
 */

public class Heroes {

    List<Hero> heroes;

    public Heroes() {
    }

    public Heroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }
}
