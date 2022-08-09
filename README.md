# libBottomDialog [![](https://jitpack.io/v/Tobeyr1/libBottomDialog.svg)](https://jitpack.io/#Tobeyr1/libBottomDialog)
一个仿IOS的底部选择器及底部弹出框警告框
---------------------------
# renderings

![在这里插入图片描述](https://img-blog.csdnimg.cn/b46a83d7086240faacaafd5b1efdb88d.gif#pic_center =350x680)

![在这里插入图片描述](https://img-blog.csdnimg.cn/75cc779db49247909f0719861946528f.jpeg#pic_center =350x680)

![在这里插入图片描述](https://img-blog.csdnimg.cn/a2cb96d8b9c9487f8d2e5b0df90c1247.jpeg#pic_center =350x680)
---------------------------
# Quick Setup
**Add it in your root build.gradle at the end of repositories:**

```java
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```
**Then add the dependency:**
```java
dependencies {
	        implementation 'com.github.Tobeyr1:libBottomDialog:1.0.0-alpha'
	}
```
# Basic Usage
```java
 BottomAlertDialog dialog = new BottomAlertDialog()
                    .openFullScreenMask(false,R.color.purple_200,0.1f)
                    .setBackGroundColor(R.color.purple_200)
                    .setCancelBtnGone()
                    .setContent("Java content")
                    .setTitle("Java title")
                    .setTitleColor(R.color.white)
                    .setGravityStyle(Gravity.BOTTOM)
                    .setPositiveButtonMethod("Ok",R.color.white,(d,view) ->{
                        d.dismiss();
                        return null;
                    })
                    .setFragmentManager(getSupportFragmentManager())
                    ;
            dialog.show();
```
```java
  BottomChoiceDialog bottomChoiceDialog = new BottomChoiceDialog(
                    arrayList,(position,value) ->{
                        switch (position){
                            case 0 :
                                Toast.makeText(this, "take photo", Toast.LENGTH_SHORT).show();
                                break;
                            case 1 :
                                Toast.makeText(this, "Select from library", Toast.LENGTH_SHORT).show();
                                break;
                            case 2 :
                                Toast.makeText(this, "View image full Screen", Toast.LENGTH_SHORT).show();
                                break;
                        }
                return null;
            });
            bottomChoiceDialog.show(getSupportFragmentManager(),"dialog");
```
# 中间圆角样式
[在这里插入图片描述](https://img-blog.csdnimg.cn/558f24c6fd8848a99177e093dd1db3b7.jpeg#pic_center =350x680)

```java
 BottomAlertDialog dialog = new BottomAlertDialog()
                    .openFullScreenMask(false, R.color.color_transparent,0.1f)
                    .setBackGroundColor(R.drawable.bg_dialog_full_radius_14_white)
                    .setContent("Exiting out of this form will remove all record information. Would you like to exit anyway?")
                    .setConfirmBg(R.drawable.bg_button_full_green_408)
                    .setCancelBg(R.drawable.bg_button_login_full_border_black_stroke_2)
                    .setTitle("Java title")
                    .setTitleColor(R.color.black)
                    .setGravityStyle(Gravity.CENTER)
                    .setCanceledOnTouchOutside(false)
                    .setWidth(getScreenWidth()-dpToPx(32))//单独设置宽度
                    //.setWindowSize(getScreenWidth()-dpToPx(32),dpToPx(280)) //整体size
                    .setPositiveButtonMethod("Ok",R.color.white,(d,view) ->{
                        d.dismiss();
                        return null;
                    })
                    .setNegativeButtonMethod("Cancel",R.color.black,(d,view) ->{
                        d.dismiss();
                        return null;
                    })
                    .setFragmentManager(getSupportFragmentManager())
                    ;
            dialog.show();
            
    private int getScreenWidth(){
        return getResources().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight(){
        return getResources().getDisplayMetrics().heightPixels;
    }

    private int dpToPx(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }
```

# 中间圆角+弹窗阴影效果
![在这里插入图片描述](https://img-blog.csdnimg.cn/569900c89ca74aa68719bd2e892c7170.jpeg#pic_center =350x680)

```java
 BottomAlertDialog dialog = new BottomAlertDialog()
                    .openFullScreenMask(false, R.drawable.bg_dialog_full_radius_14_white,0.1f)//同时使用圆角shape
                    .setBackGroundColor(R.drawable.bg_dialog_full_radius_14_white)
                    //.setCancelBtnGone() //是否隐藏取消按钮
                    .setContent("Exiting out of this form will remove all record information. Would you like to exit anyway?")
                    .setConfirmBg(R.drawable.bg_button_full_green_408)
                    .setCancelBg(R.drawable.bg_button_login_full_border_black_stroke_2)
                    .setTitle("Java title")
                    .setTitleColor(R.color.black)
                    .setGravityStyle(Gravity.CENTER)
                    .setCanceledOnTouchOutside(false)
                    .setWidth(getScreenWidth()-dpToPx(32))
                    //.setWindowSize(getScreenWidth()-dpToPx(32),dpToPx(280))
                    .setPositiveButtonMethod("Ok",R.color.white,(d,view) ->{
                        d.dismiss();
                        return null;
                    })
                    .setNegativeButtonMethod("Cancel",R.color.black,(d,view) ->{
                        d.dismiss();
                        return null;
                    })
                    .setFragmentManager(getSupportFragmentManager())
                    ;
            dialog.show();
```
### bg_dialog_full_radius_14_white.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <corners android:radius="14dp" />
    <solid android:color="#FFFFFF" />
</shape>
```
