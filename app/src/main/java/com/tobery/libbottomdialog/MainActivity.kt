package com.tobery.libbottomdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.bt_choice).setOnClickListener {
            val dialogBottom = BottomAlertDialog()
                .setTitle(getString(R.string.bottom_title))
                .setContent("This is content ${System.currentTimeMillis()}")
                .setFragmentManager(supportFragmentManager)
                .setGravityStyle(Gravity.BOTTOM)
                //.setCancelBtnGone() 是否隐藏取消按钮
                .setBackGroundColor(R.color.blue_33007A)//设置dialog背景
                //.setCancelColor()设置cancel 文本颜色
                //.setCancelBg()设置cancel按钮背景色
                //.setConfirmTextColor()设置确认文本颜色
                //.setConfirmBg()设置确认按钮背景色
                .setCanceledOnTouchOutside(false)
                .openFullScreenMask(false,R.color.purple_200,0.1f) //是否开启全屏以及弹框底部虚拟背景色和透明度
                //.setTitleColor()设置标题、内容文本颜色
                .setNegativeButtonMethod("cancel", R.color.purple_200){dialog, _ ->
                    dialog?.dismiss()
                }
                .setPositiveButtonMethod("OK",R.color.white){dialog,_ ->
                    dialog?.dismiss()
                }
            dialogBottom.show()


        }

        findViewById<Button>(R.id.bt_bottom).setOnClickListener {
            BottomChoiceDialog(
                data = listOf(
                    "Take Photo",
                    "Select from library",
                    "View image full Screen"
                )
            ){position, value ->  
                when(position){

                }
            }.show(supportFragmentManager,"imgDialog")
        }
    }
}