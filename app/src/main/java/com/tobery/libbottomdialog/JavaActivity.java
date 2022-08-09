package com.tobery.libbottomdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        ArrayList arrayList = new ArrayList();
        arrayList.add("Take Photo");
        arrayList.add("Select from library");
        arrayList.add("View image full Screen");
        findViewById(R.id.bt_choice).setOnClickListener(v -> {
            BottomAlertDialog dialog = new BottomAlertDialog()
                    .openFullScreenMask(false, R.drawable.bg_dialog_full_radius_14_white,0.1f)
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

        });

        findViewById(R.id.bt_bottom).setOnClickListener(v -> {
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
        });
    }

    private int getScreenWidth(){
        return getResources().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight(){
        return getResources().getDisplayMetrics().heightPixels;
    }

    private int dpToPx(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }
}