package com.androidtitan.simplehero;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by amohnacs on 10/22/16.
 */

public class ImageDownloader {
    private final String TAG = getClass().getSimpleName();

    public ImageDownloader() {

    }

    public void imageDownload(String url, ImageView imageView) {

        if(cancelDuplicateDownload(url, imageView)) {
            BitmapDownloaderAsyncTask asyncTask = new BitmapDownloaderAsyncTask(imageView);
            DownloadDrawable downloadDrawable = new DownloadDrawable(asyncTask);
            imageView.setImageDrawable(downloadDrawable);
            asyncTask.execute(url);
        }
    }

    private static BitmapDownloaderAsyncTask getBitmapDownloaderTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadDrawable) {
                DownloadDrawable downloadedDrawable = (DownloadDrawable)drawable;
                return downloadedDrawable.getBitmapDownloadTask();
            }
        }
        return null;
    }

    private static boolean cancelDuplicateDownload(String url, ImageView imageView) {
        BitmapDownloaderAsyncTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

        if (bitmapDownloaderTask != null) {
            String bitmapUrl = bitmapDownloaderTask.url;
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                bitmapDownloaderTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }
        return true;
    }

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

    static class DownloadDrawable extends ColorDrawable { //can we change this to a Bitmap Drawable
        private final WeakReference<BitmapDownloaderAsyncTask> weakBitmapAsyncTask;

        public DownloadDrawable(BitmapDownloaderAsyncTask task) {
            super(Color.LTGRAY);
            weakBitmapAsyncTask = new WeakReference<BitmapDownloaderAsyncTask>(task);
        }

        public BitmapDownloaderAsyncTask getBitmapDownloadTask() {
            return weakBitmapAsyncTask.get();
        }
    }
}
