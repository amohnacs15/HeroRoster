package com.androidtitan.simplehero.threads;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

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
            String bitmapUrl = url;
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                bitmapDownloaderTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }
        return true;
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
