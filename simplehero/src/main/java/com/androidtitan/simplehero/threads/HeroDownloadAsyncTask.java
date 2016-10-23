package com.androidtitan.simplehero.threads;

import android.os.AsyncTask;
import android.util.Log;

import com.androidtitan.simplehero.model.SuperHero;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by amohnacs on 10/22/16.
 */

public class HeroDownloadAsyncTask extends AsyncTask<String, Void, String> {
    private final String TAG = getClass().getSimpleName();

    private AsyncTaskInterface asyncTaskListener;

    public HeroDownloadAsyncTask(AsyncTaskInterface passedInterface) {
        asyncTaskListener = passedInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Log.e(TAG, "AsyncTask Running...");


    }

    @Override
    protected String doInBackground(String... params) {

        URL url = null;
        String strTemp = "";
        String returnString = "";

        try {
            url = new URL(generateUrl(params[0], params[1]));

            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            while (null != (strTemp = br.readLine())) {
                returnString = strTemp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnString;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //parse our JSON into our array of Heroes
        //notify our adapter of changes here
        Log.e(TAG, s);

        ArrayList<SuperHero> heroList = new ArrayList<SuperHero>();

        try {
            JSONObject heroResponse = new JSONObject(s);

            if(heroResponse.get("data") instanceof JSONObject) {
                JSONObject data = heroResponse.getJSONObject("data");

                if(data.get("results") instanceof JSONArray) {
                    JSONArray heroes = data.getJSONArray("results");

                    for (int i = 0; i < heroes.length(); i++){

                        SuperHero hero = new SuperHero();

                        if(heroes.getJSONObject(i).get("name") instanceof String) {
                           hero.setName(heroes.getJSONObject(i).getString("name"));
                        }

                        if(heroes.getJSONObject(i).get("description") instanceof String) {
                            hero.setDescription(heroes.getJSONObject(i).getString("description"));
                        }

                        if(heroes.getJSONObject(i).get("thumbnail") instanceof JSONObject) {
                            JSONObject jsonThumbnail = heroes.getJSONObject(i).getJSONObject("thumbnail");

                            if(jsonThumbnail.get("path") instanceof String) {
                                hero.setImageUrl(jsonThumbnail.getString("path"));
                            }

                            if(jsonThumbnail.get("extension") instanceof String) {
                                hero.setImageExtension(jsonThumbnail.getString("extension"));
                            }
                        }

                        heroList.add(hero);
                    }

                    asyncTaskListener.updateAdapter(heroList);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        asyncTaskListener = null;
    }

    private String generateUrl(String privateKey, String publicKey) {
        long timeStamp = System.currentTimeMillis();

        String stringToConvert = timeStamp + privateKey + publicKey;
        String hash = md5(stringToConvert);

        String url = String.format("https://gateway.marvel.com:443/v1/public/characters?orderBy=-modified&&limit=20&" +
                "ts=%d&apikey=%s&hash=%s", timeStamp, publicKey, hash);

        return url;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
