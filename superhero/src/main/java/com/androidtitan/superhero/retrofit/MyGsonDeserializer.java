package com.androidtitan.superhero.retrofit;

import com.androidtitan.superhero.model.Hero;
import com.androidtitan.superhero.model.Heroes;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by amohnacs on 10/23/16.
 */

public class MyGsonDeserializer implements JsonDeserializer <Heroes> {

    @Override
    public Heroes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject response = json.getAsJsonObject();
        JsonArray heroArray = response.getAsJsonObject("data").getAsJsonArray("results");
        Hero[] heroesArray = context.deserialize(heroArray, Hero[].class);

        List<Hero> heroes = new ArrayList<>();

        for(Hero heroItem : heroesArray) {
            heroes.add(heroItem);
        }

        return new Heroes(heroes);

    }
}
