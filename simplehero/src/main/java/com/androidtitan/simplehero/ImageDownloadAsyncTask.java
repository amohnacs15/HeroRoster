package com.androidtitan.simplehero;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by amohnacs on 10/22/16.
 */

public class ImageDownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private final String TAG = getClass().getSimpleName();

    private final WeakReference<ImageView> imageView;

    public ImageDownloadAsyncTask(ImageView passedImageView) {
        imageView = new WeakReference<ImageView>(passedImageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        HttpURLConnection urlConnection = null;

        String uriString = params[0] + "/portrait_incredible." + params[1];

        try {
            URL uri = new URL(uriString);
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
        return null;

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if(isCancelled()) {
            bitmap = null;
        }

        if(imageView != null) {
            ImageView newImageView = imageView.get();

            if (bitmap != null) {
                newImageView.setImageBitmap(bitmap);
            }
        }

    }
}
