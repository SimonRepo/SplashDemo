Android SplashView
==================

![](https://github.com/SimonRepo/SplashDemo/raw/master/screenshots/device-2018-04-23-100058.gif)

## Function
- XsSplashView which can countdown advertising,listen jump callback.
- XsSplashView which can custom exit animation .

## Sample Application
<a href="https://github.com/SimonRepo/demo-apk/raw/master/image/SplashDemo-apk-qrcode.png" target="_blank" title="Scan to download"><img src="https://github.com/SimonRepo/demo-apk/raw/master/image/SplashDemo-apk-qrcode.png" title="Scan to download"/></a>
<a href="https://github.com/SimonRepo/demo-apk/raw/master/apk/SplashDemo.apk" target="_blank" title="Click to download">Apk Download</a>  

## Usage
- update splash data
```
XsSplashHelper.downLoadSplash(this,"http://pic.58pic.com/58pic/14/64/56/25h58PIC3eG_1024.jpg",
                "https://simonrepo.github.io");
```

- show splash
```
     XsSplashHelper.getBuilder(this)
                .link("https://simonrepo.github.io")
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
```
## Setting
- `link(String)` set jump advertising links.
- `defaultRes(Integer)` set default advertising pic.
- `countDown(int)` set countDown,default is 3s.
- `textSizeDp(int)` default is 10.
- `textBackgroundSizeDp(int)` default is 35.
- `textMarginDp(int)` default is 12.
- `textColorRes(int)` set countDown text color,default is #ffffff.
- `textBackgroudColorRes(int)` set countDown background color,default is #b6bdcc.
- `dismissAnimation(Animation)` set dismiss animation,default is AlphaAnimation.
- `listenr(XsSplashView.OnClickSplashListener)` set listen callback.

## Download
Maven:  
``` xml
<dependency>
  <groupId>com.xs.splashview</groupId>
  <artifactId>splashview</artifactId>
  <version>1.0.2</version>
  <type>pom</type>
</dependency>
```  

Gradle:  
``` xml
compile 'com.xs.splashview:splashview:1.0.2'
```  

Demo File: [AutoScrollViewPagerSingleDemo.java](https://github.com/Trinea/android-demo/blob/master/src/cn/trinea/android/demo/AutoScrollViewPagerDemo.java)  
Demo Project: [android-demo](https://github.com/Trinea/android-demo)  


