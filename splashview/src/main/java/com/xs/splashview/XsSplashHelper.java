package com.xs.splashview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.animation.Animation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Simon on 2018/3/30.
 * Description: ...
 */

public class XsSplashHelper {

    private static XsSplashView xsSplashView;

    private XsSplashHelper(){}

    public static Builder getBuilder(Activity context){
        return new Builder(context);
    }

    public static void destroy() {
        if (xsSplashView == null)
            return;
        xsSplashView.forceDismiss();
        xsSplashView = null;
    }


    public static class Builder {

        private String link;
        private Integer defaultSplashRes;
        private int countDown = 3;
        private int textColorRes = Color.parseColor("#ffffff");
        private int textBackgroudColorRes = Color.parseColor("#b6bdcc");
        private int textSizeDp = 10;
        private int textBackgroundSizeDp = 35;
        private int textMarginDp = 12;
        private Animation dismissAnimation;
        private XsSplashView.OnClickSplashListener onClickSplashListener;
        private Activity context;

        public Builder(@NonNull Activity context){
            this.context = context;
        }


        public Builder link(String link){
            this.link = link;
            return this;
        }

        public Builder defaultRes(Integer defaultSplashRes){
            this.defaultSplashRes = defaultSplashRes;
            return this;
        }

        public Builder countDown(int countDown){
            this.countDown = countDown;
            return this;
        }

        public Builder textColorRes(int textColorRes){
            this.textColorRes = textColorRes;
            return this;
        }

        public Builder textBackgroudColorRes(int textBackgroudColorRes){
            this.textBackgroudColorRes = textBackgroudColorRes;
            return this;
        }

        public Builder textSizeDp(int textSizeDp){
            this.textSizeDp = textSizeDp;
            return this;
        }

        public Builder textBackgroundSizeDp(int textBackgroundSizeDp){
            this.textBackgroundSizeDp = textBackgroundSizeDp;
            return this;
        }

        public Builder textMarginDp(int textMarginDp){
            this.textMarginDp = textMarginDp;
            return this;
        }

        public Builder listenr(XsSplashView.OnClickSplashListener onClickSplashListener){
            this.onClickSplashListener = onClickSplashListener;
            return this;
        }

        public Builder dismissAnimation(Animation dismissAnimation){
            this.dismissAnimation = dismissAnimation;
            return this;
        }

        public XsSplashView build(){
            if (xsSplashView == null)
                xsSplashView = new XsSplashView(context);
            xsSplashView.setDefaultSplashRes(defaultSplashRes);
            xsSplashView.setLink(link);
            xsSplashView.setCountdown(countDown);
            xsSplashView.setTextColorRes(textColorRes);
            xsSplashView.setTextBackgroundColorRes(textBackgroudColorRes);
            xsSplashView.setTextSizeDp(textSizeDp);
            xsSplashView.setTextBackgroundSizeDp(textBackgroundSizeDp);
            xsSplashView.setTextMarginDp(textMarginDp);
            if (dismissAnimation != null)
                xsSplashView.setDismissAnimation(dismissAnimation);
            if (onClickSplashListener != null)
                xsSplashView.setOnClickSplashListener(onClickSplashListener);
            return xsSplashView;
        }
    }

    public static void downLoadSplash(Activity context,String imgUrl) {
        DownLoadTask task = new DownLoadTask(context);
        task.execute(imgUrl);
    }

    private static class DownLoadTask extends AsyncTask<String,Void,Integer> {

        private Activity activity;

        public DownLoadTask(Activity activity){
            this.activity = activity;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }

        @Override
        protected Integer doInBackground(String... urls) {
            URL imgUrl;
            Bitmap bitmap;
            InputStream is = null;
            BufferedOutputStream bos = null;
            try {
                imgUrl = new URL(urls[0]);
                HttpURLConnection urlConn = (HttpURLConnection) imgUrl.openConnection();
                urlConn.setDoInput(true);
                urlConn.connect();
                is = urlConn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);

                File localFile = new File(activity.getFilesDir().getAbsolutePath().toString() + "/splash.jpg");
                bos = new BufferedOutputStream(new FileOutputStream(localFile));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bos != null){
                    try {
                        bos.flush();
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return 0;
        }
    }

}
