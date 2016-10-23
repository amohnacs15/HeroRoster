package com.androidtitan.simplehero.threads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by amohnacs on 10/23/16.
 */

class BitmapDownloaderAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private String url;
    private final WeakReference<ImageView> weakImageView;

    public BitmapDownloaderAsyncTask(ImageView imageView) {
        weakImageView = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        HttpURLConnection urlConnection = null;

        try {
            URL uri = new URL(params[0]);
            urlConnection = (HttpURLConnection) uri.openConnection();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null; //parameters are given in an array
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (weakImageView != null) {
            ImageView imageView = weakImageView.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
