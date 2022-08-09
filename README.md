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
