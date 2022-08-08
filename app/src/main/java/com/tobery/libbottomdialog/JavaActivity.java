package com.tobery.libbottomdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
}