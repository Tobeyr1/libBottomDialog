package com.tobery.libbottomdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("Take Photo");
        arrayList.add("Select from library");
        arrayList.add("View image full Screen");
        findViewById(R.id.bt_choice).setOnClickListener(v -> {

        });

        findViewById(R.id.bt_bottom).setOnClickListener(v -> {
            BottomChoiceDialog bottomChoiceDialog = new BottomChoiceDialog(
                    arrayList,(a,b) ->{

                return null;
            });
        });
    }
}