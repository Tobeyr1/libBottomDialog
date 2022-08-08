# libBottomDialog [![](https://jitpack.io/v/Tobeyr1/libBottomDialog.svg)](https://jitpack.io/#Tobeyr1/libBottomDialog
一个仿IOS的底部选择器及底部弹出框警告框
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
