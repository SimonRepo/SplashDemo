package com.xs.splashview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Simon on 2018/3/30.
 * Description: 广告页View
 */

public class XsSplashView extends FrameLayout {

    private int countdown;//倒计时
    private Integer defaultSplashRes;//默认图
    private String link;//跳转链接
    private int textColorRes;
    private int textBackgroundColorRes;
    private int textSizeDp;
    private int textBackgroundSizeDp;
    private int textMarginDp;
    private Animation dismissAnimation;
    private TextView tvCountDown;
    private ImageView ivSplash;
    private OnClickSplashListener onClickSplashListener;
    private Activity context;

    public XsSplashView(@NonNull Activity context) {
        super(context);
        initView(context);
    }

    public XsSplashView(@NonNull Activity context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public XsSplashView(@NonNull Activity context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setDefaultSplashRes(Integer defaultSplashRes){
        this.defaultSplashRes = defaultSplashRes;
    }

    public void setLink(String link){
        this.link = link;
    }

    public void setCountdown(int countdown){
        this.countdown = countdown;
    }

    public void setTextColorRes(int textColorRes){
        this.textColorRes = textColorRes;
    }

    public void setTextBackgroundColorRes(int textBackgroundColorRes){
        this.textBackgroundColorRes = textBackgroundColorRes;
    }

    public void setTextSizeDp(int textSizeDp){
        this.textSizeDp = textSizeDp;
    }

    public void setTextBackgroundSizeDp(int textBackgroundSizeDp){
        this.textBackgroundSizeDp = textBackgroundSizeDp;
    }

    public void setTextMarginDp(int textMarginDp){
        this.textMarginDp = textMarginDp;
    }

    public void setOnClickSplashListener(OnClickSplashListener onClickSplashListener){
        this.onClickSplashListener = onClickSplashListener;
    }

    private void initView(@NonNull Activity context){
        this.context = context;
        ivSplash = new ImageView(context);
        ivSplash.setScaleType(ImageView.ScaleType.FIT_XY);
        LayoutParams ivLayoutParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(ivSplash,ivLayoutParam);
        tvCountDown = new TextView(context);
    }

    public void show(){
        this.addView(tvCountDown,setTextViewData(context));

        ViewGroup viewGroup = context.getWindow().getDecorView().findViewById(android.R.id.content);
        if (viewGroup == null || viewGroup.getChildCount() == 0)
            return;
        if (!isNeedToContinue())
            return;

        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setSplashBitmap();

        LayoutParams thisLayoutParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(this,thisLayoutParam);
        this.post(runnable);
    }

    private LayoutParams setTextViewData(@NonNull Activity context){
        int countDownMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textMarginDp, context.getResources().getDisplayMetrics());
        int countDownLayoutSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textBackgroundSizeDp, context.getResources().getDisplayMetrics());
        tvCountDown.setTextSize(TypedValue.COMPLEX_UNIT_DIP,textSizeDp);
        tvCountDown.setTextColor(textColorRes);
        tvCountDown.setGravity(Gravity.CENTER);

        LayoutParams tvLayoutParam = new LayoutParams(countDownLayoutSize,countDownLayoutSize);
        tvLayoutParam.gravity = Gravity.TOP | Gravity.RIGHT;
        tvLayoutParam.setMargins(0, countDownMargin, countDownMargin, 0);

        GradientDrawable countDownBg = new GradientDrawable();
        countDownBg.setShape(GradientDrawable.OVAL);
        countDownBg.setColor(textBackgroundColorRes);
        tvCountDown.setBackgroundDrawable(countDownBg);

        tvCountDown.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        ivSplash.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(link) && onClickSplashListener != null)
                    onClickSplashListener.clickSplash(link);
            }
        });

        return tvLayoutParam;
    }

    private void setSplashBitmap(){
        String filePath = context.getFilesDir().getAbsolutePath().toString() + "/splash.jpg";
        Bitmap bitmap;
        if (isExistLocalSplashFile(filePath)) {
            bitmap = BitmapFactory.decodeFile(filePath);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(),defaultSplashRes);
        }
        ivSplash.setImageBitmap(bitmap);
    }

    private boolean isNeedToContinue(){
        String filePath = context.getFilesDir().getAbsolutePath().toString() + "/splash.jpg";
        if (!isExistLocalSplashFile(filePath) && (defaultSplashRes == null || defaultSplashRes == 0)){
            return false;
        } else {
            return true;
        }
    }

    private Runnable runnable = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            if (countdown == 0){
                tvCountDown.setText(String.format("跳过\n%d s", 0));
                dismiss();
            } else {
                tvCountDown.setText(String.format("跳过\n%d s", countdown--));
                XsSplashView.this.postDelayed(this,1000);
            }
        }
    };

    public void dismiss() {
        if (onClickSplashListener != null)
            onClickSplashListener.jumpOver();

        XsSplashView.this.removeCallbacks(runnable);
        ViewGroup viewGroup = (ViewGroup) this.getParent();
        if (dismissAnimation == null) {
            dismissAnimation = new AlphaAnimation(1.0f, 0f);
            dismissAnimation.setDuration(1000);
        }
        setAnimation(dismissAnimation);
        viewGroup.removeView(this);
    }

    public void forceDismiss(){
        XsSplashView.this.removeCallbacks(runnable);
        ViewGroup viewGroup = (ViewGroup) this.getParent();
        viewGroup.removeView(this);
    }


    public void setDismissAnimation(Animation animation){
        this.dismissAnimation = animation;
    }

    public boolean isExistLocalSplashFile(String filePath) {
        if(TextUtils.isEmpty(filePath)) {
            return false;
        } else {
            File file = new File(filePath);
            return file.exists() && file.isFile();
        }
    }

    public interface OnClickSplashListener {
        void jumpOver();
        void clickSplash(String link);
    }

}
