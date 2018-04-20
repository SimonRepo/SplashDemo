package com.xs.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.xs.splashview.XsSplashHelper;
import com.xs.splashview.XsSplashView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XsSplashHelper.destroy();
    }

    public void showSplash(View view) {
        ScaleAnimation myAnimation_Scale;
        myAnimation_Scale =new ScaleAnimation(1f, 0f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        myAnimation_Scale.setDuration(1000);

        XsSplashHelper.getBuilder(this)
                .link("https://simonrepo.github.io")
//                .defaultRes(R.mipmap.ic_launcher)//若不设置默认图 且本地无广告的情况下 则不会显示广告页
//                .countDown(5)//若不设置 则默认倒计时3S
//                .textSizeDp(10)
//                .textBackgroundSizeDp(35)
//                .textMarginDp(12)
//                .textColorRes(Color.parseColor("#ffffff"))
//                .textBackgroudColorRes(Color.parseColor("#888888"))
//                .dismissAnimation(myAnimation_Scale)//若不设置 则默认为淡出动画
                .listenr(new XsSplashView.OnClickSplashListener() {
                    @Override
                    public void jumpOver() {
                        Log.e("info","jumpOver:-> ");
                    }

                    @Override
                    public void clickSplash(String link) {
                        Log.e("info","clickSplash:-> ");
                        WebActivity.start(MainActivity.this,link);
                    }
                })
                .build().show();
    }

    public void updateLocalSplash(View view) {
        XsSplashHelper.downLoadSplash(this,"http://pic.58pic.com/58pic/14/64/56/25h58PIC3eG_1024.jpg",
                "https://simonrepo.github.io");
    }
}
