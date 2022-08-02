package com.tobery.libbottomdialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


class BottomAlertDialog : DialogFragment() {

    companion object {
        const val BASE_DIALOG_TAG = "BASE_DIALOG"
        private var mFragment: FragmentManager? = null
        private var mNegativeButtonMethod: ((Dialog?, View?) -> Unit)? = null
        private var mPositiveButtonMethod: ((Dialog?, View?) -> Unit)? = null
    }

    private lateinit var tvTitle: TextView
    private lateinit var tvContent: TextView
    private lateinit var tvOkButton: TextView
    private lateinit var tvCancelButton: TextView
    private lateinit var viewBg: View
    private var tvCancelColor: Int = 0
    private var tvTitleColor: Int = 0
    private var tvConfirmColor: Int = 0
    private var confirmBg: Int = 0
    private var viewBgColor: Int = 0
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mAnimRes: Int = 0
    private var gravityStyle: Int = Gravity.BOTTOM
    private var mCancelBtnVisibility: Boolean = false
    private var mTitle: String? = null
    private var mContent: String? = null
    private var mTvCancelMsg: String? = null
    private var mTvOkmMg: String? = null
    private var mNegativeToDismiss = true
    private var mCanceledOnTouchOutside = false
    private var mNegativeButtonMethodAdded = false
    private var mPositiveButtonMethodAdded = false

    init {
        arguments = Bundle()
    }

    private val mButtonClickListener =
        View.OnClickListener { view ->
            when (view.id) {
                R.id.tv_bt_ok -> {
                    if (mPositiveButtonMethodAdded) {
                        mPositiveButtonMethod?.invoke(dialog, view)
                    }
                }
                R.id.tv_bt_cancel -> {
                    if (mNegativeToDismiss) dismiss()
                    if (mNegativeButtonMethodAdded) {
                        mNegativeButtonMethod?.invoke(dialog, view)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            mWidth = getInt("mWidth")
            mHeight = getInt("mHeight")
            mTitle = getString("mTitle")
            mContent = getString("mContent")
            tvTitleColor = getInt("tvTitleColor")
            tvCancelColor = getInt("tvCancelColor")
            tvConfirmColor = getInt("tvConfirmColor")
            confirmBg = getInt("confirmBg")
            viewBgColor = getInt("viewBgColor")
            mTvOkmMg = getString("mTvOkmMg")
            mTvCancelMsg = getString("mTvCancelMsg")
            gravityStyle = getInt("gravityStyle")
            mCancelBtnVisibility = getBoolean("mCancelBtnVisibility")
            mCanceledOnTouchOutside = getBoolean("mCanceledOnTouchOutside")
            mNegativeButtonMethodAdded = getBoolean("mNegativeButtonMethodAdded")
            mPositiveButtonMethodAdded = getBoolean("mPositiveButtonMethodAdded")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.ui_common_bottom_alert, container, false)
        tvTitle = view.findViewById(R.id.tv_title)
        tvContent = view.findViewById(R.id.tv_content)
        tvCancelButton = view.findViewById(R.id.tv_bt_cancel)
        tvOkButton = view.findViewById(R.id.tv_bt_ok)
        viewBg = view.findViewById(R.id.view_bg)
        isCancelable = mCanceledOnTouchOutside
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewBgColor > 0) viewBg.background =
            ResourcesCompat.getDrawable(resources, viewBgColor, null) //修改背景
        val showTitle = !TextUtils.isEmpty(mTitle)
        val showContent = !TextUtils.isEmpty(mContent)
        if (showTitle) {
            tvTitle.text = mTitle
        }
        if (showContent) {
            tvContent.text = mContent
            tvTitle.visibility = if (showTitle) View.VISIBLE else View.GONE
        }
        tvContent.visibility = if (showContent) View.VISIBLE else View.GONE
        if (tvCancelColor > 0) tvCancelButton.background =
            ResourcesCompat.getDrawable(resources, tvCancelColor, null)
        if (tvTitleColor > 0) {
            tvTitle.setTextColor(ResourcesCompat.getColor(resources, tvTitleColor, null))
            tvContent.setTextColor(ResourcesCompat.getColor(resources, tvTitleColor, null))
        }
        if (tvConfirmColor > 0) tvOkButton.setTextColor(
            ResourcesCompat.getColor(
                resources,
                tvConfirmColor,
                null
            )
        )
        if (confirmBg > 0) tvOkButton.background =
            ResourcesCompat.getDrawable(resources, confirmBg, null)

        if (mTvOkmMg != null) tvOkButton.text = mTvOkmMg
        if (mTvCancelMsg != null) tvCancelButton.text = mTvCancelMsg
        if (mCancelBtnVisibility) {
            tvCancelButton.visibility = View.GONE
            val lp: ConstraintLayout.LayoutParams =
                tvOkButton.layoutParams as ConstraintLayout.LayoutParams
            lp.leftMargin = resources.getDimensionPixelSize(R.dimen.dp_24)
            lp.rightMargin = resources.getDimensionPixelSize(R.dimen.dp_24)
            tvOkButton.layoutParams = lp
        }
        tvOkButton.setOnClickListener(mButtonClickListener)
        tvCancelButton.setOnClickListener(mButtonClickListener)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog ?: return
        val window = dialog.window ?: return
        val params = window.attributes
        //set background transparent
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //set anim
        if (mAnimRes > 0) window.setWindowAnimations(mAnimRes)
        //set dialog width and height
        params.width = if (mWidth > 0) mWidth else WindowManager.LayoutParams.MATCH_PARENT
        if (mHeight > 0) params.height = mHeight
        //set transparent
        params.dimAmount = 0.0f
        params.gravity = gravityStyle
        window.attributes = params
    }

    fun setFragmentManager(fragmentManager: FragmentManager): BottomAlertDialog {
        mFragment = fragmentManager
        return this
    }

    fun setWindowSize(width: Int, height: Int): BottomAlertDialog {
        setWidth(width)
        setHeight(height)
        return this
    }

    fun setWidth(width: Int): BottomAlertDialog {
        mWidth = width
        arguments?.putInt("mWidth", mWidth)
        return this
    }

    fun setCancelColor(color: Int): BottomAlertDialog {
        tvCancelColor = color
        arguments?.putInt("tvCancelColor", tvCancelColor)
        return this
    }

    fun setTitleColor(color: Int) = apply {
        tvTitleColor = color
        arguments?.putInt("tvTitleColor", tvTitleColor)
    }

    fun setConfirmBg(drawable: Int) = apply {
        confirmBg = drawable
        arguments?.putInt("confirmBg", confirmBg)
    }

    fun setConfirmTextColor(color: Int) = apply {
        tvConfirmColor = color
        arguments?.putInt("tvConfirmColor", tvConfirmColor)
    }

    fun setCancelBtnGone(): BottomAlertDialog {
        mCancelBtnVisibility = true
        arguments?.putBoolean("mCancelBtnVisibility", true)
        return this
    }

    fun setHeight(height: Int): BottomAlertDialog {
        mHeight = height
        arguments?.putInt("mHeight", mHeight)
        return this
    }

    fun setBackGroundColor(color: Int) = apply {
        viewBgColor = color
        arguments?.putInt("viewBgColor", viewBgColor)
    }

    fun setGravityStyle(gravity: Int): BottomAlertDialog {
        gravityStyle = gravity
        arguments?.putInt("gravityStyle", gravityStyle)
        return this
    }

    fun setAnimStyle(@StyleRes animStyle: Int): BottomAlertDialog {
        mAnimRes = animStyle
        arguments?.putInt("mAnimRes", mAnimRes)
        return this
    }

    fun setTitle(title: String): BottomAlertDialog {
        mTitle = title
        arguments?.putString("mTitle", mTitle)
        return this
    }

    fun setContent(content: String): BottomAlertDialog {
        mContent = content
        arguments?.putString("mContent", mContent)
        return this
    }

    fun setNegativeToDismiss(negativeToDismiss: Boolean): BottomAlertDialog {
        mNegativeToDismiss = negativeToDismiss
        arguments?.putBoolean("mNegativeToDismiss", mNegativeToDismiss)
        return this
    }

    fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean): BottomAlertDialog {
        mCanceledOnTouchOutside = canceledOnTouchOutside
        arguments?.putBoolean("mCanceledOnTouchOutside", mCanceledOnTouchOutside)
        return this
    }

    fun setNegativeButtonMethod(
        msg: String?,
        negativeButtonMethod: (Dialog?, View?) -> Unit
    ): BottomAlertDialog {
        mTvCancelMsg = msg
        msg?.let { arguments?.putString("mTvCancelMsg", it) }
        mNegativeButtonMethod = negativeButtonMethod
        arguments?.putBoolean("mNegativeButtonMethodAdded", true)
        return this
    }

    fun setPositiveButtonMethod(
        msg: String?,
        tvColor: Int? = null,
        positiveButtonMethod: (Dialog?, View?) -> Unit
    ): BottomAlertDialog {
        mTvOkmMg = msg
        mPositiveButtonMethod = positiveButtonMethod
        msg?.let { arguments?.putString("mTvOkmMg", it) }
        tvColor?.let {
            tvConfirmColor = it
            arguments?.putInt("tvConfirmColor", tvConfirmColor)
        }
        arguments?.putBoolean("mPositiveButtonMethodAdded", true)
        return this
    }

    fun show() {
        mFragment?.let {
            val ft = it.beginTransaction()
            val prev = it.findFragmentByTag(BASE_DIALOG_TAG)
            if (prev != null) ft.remove(prev)
            ft.add(this, BASE_DIALOG_TAG)
            ft.commitAllowingStateLoss()
        }
    }

}