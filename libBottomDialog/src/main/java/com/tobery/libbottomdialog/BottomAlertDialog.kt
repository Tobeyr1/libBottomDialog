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
    private var cancelBg: Int = 0
    private var viewBgColor: Int = 0
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var isOpenMask: Boolean = false
    private var maskColor: Int = 0
    private var dimAmount: Float = 0.5f
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
            cancelBg = getInt("cancelBg")
            viewBgColor = getInt("viewBgColor")
            maskColor = getInt("maskColor")
            isOpenMask = getBoolean("isOpenMask")
            dimAmount = getFloat("alpha")
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
        if (tvCancelColor > 0) tvCancelButton.setTextColor(
            ResourcesCompat.getColor(
                resources,
                tvCancelColor,
                null
            )
        )
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
        if (cancelBg > 0) tvCancelButton.background =
            ResourcesCompat.getDrawable(resources, cancelBg, null)
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
        dialog?.window?.apply {
            //set background transparent
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (maskColor > 0) setBackgroundDrawableResource(maskColor)
            //set anim
            if (mAnimRes > 0) setWindowAnimations(mAnimRes)
            val params = attributes
            params.width = if (mWidth > 0) mWidth else WindowManager.LayoutParams.MATCH_PARENT
            if (mHeight > 0) params.height = mHeight
            if (isOpenMask) params.height = WindowManager.LayoutParams.MATCH_PARENT
            //set transparent
            params.dimAmount = dimAmount
            params.gravity = gravityStyle
            attributes = params
        }
    }

    fun setFragmentManager(fragmentManager: FragmentManager) = apply {
        mFragment = fragmentManager
    }

    fun openFullScreenMask(isOpen: Boolean = false, maskColor: Int, alpha: Float) =
        apply { //是否开启全屏显示遮罩
            isOpenMask = isOpen
            this.maskColor = maskColor
            dimAmount = alpha
            arguments?.putInt("maskColor", maskColor)
            arguments?.putBoolean("isOpenMask", isOpenMask)
            arguments?.putFloat("alpha", dimAmount)
        }


    fun setWindowSize(width: Int, height: Int) = apply {
        setWidth(width)
        setHeight(height)
    }

    fun setWidth(width: Int) = apply {
        mWidth = width
        arguments?.putInt("mWidth", mWidth)
    }

    fun setCancelColor(color: Int) = apply {
        tvCancelColor = color
        arguments?.putInt("tvCancelColor", tvCancelColor)
    }

    fun setTitleColor(color: Int) = apply {
        tvTitleColor = color
        arguments?.putInt("tvTitleColor", tvTitleColor)
    }

    fun setConfirmBg(drawable: Int) = apply {
        confirmBg = drawable
        arguments?.putInt("confirmBg", confirmBg)
    }

    fun setCancelBg(drawable: Int) = apply {
        cancelBg = drawable
        arguments?.putInt("cancelBg",cancelBg)
    }

    fun setConfirmTextColor(color: Int) = apply {
        tvConfirmColor = color
        arguments?.putInt("tvConfirmColor", tvConfirmColor)
    }

    fun setCancelBtnGone() = apply {
        mCancelBtnVisibility = true
        arguments?.putBoolean("mCancelBtnVisibility", true)
    }

    fun setHeight(height: Int) = apply {
        mHeight = height
        arguments?.putInt("mHeight", mHeight)
    }

    fun setBackGroundColor(color: Int) = apply {
        viewBgColor = color
        arguments?.putInt("viewBgColor", viewBgColor)
    }

    fun setGravityStyle(gravity: Int) = apply {
        gravityStyle = gravity
        arguments?.putInt("gravityStyle", gravityStyle)
    }

    fun setAnimStyle(@StyleRes animStyle: Int) = apply {
        mAnimRes = animStyle
        arguments?.putInt("mAnimRes", mAnimRes)
    }

    fun setTitle(title: String) = apply {
        mTitle = title
        arguments?.putString("mTitle", mTitle)
    }

    fun setContent(content: String) = apply {
        mContent = content
        arguments?.putString("mContent", mContent)
    }

    fun setNegativeToDismiss(negativeToDismiss: Boolean) = apply {
        mNegativeToDismiss = negativeToDismiss
        arguments?.putBoolean("mNegativeToDismiss", mNegativeToDismiss)
    }

    fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean) = apply {
        mCanceledOnTouchOutside = canceledOnTouchOutside
        arguments?.putBoolean("mCanceledOnTouchOutside", mCanceledOnTouchOutside)
    }

    fun setNegativeButtonMethod(
        msg: String?,
        tvColor: Int? = null,
        negativeButtonMethod: (Dialog?, View?) -> Unit
    ) = apply {
        mTvCancelMsg = msg
        msg?.let { arguments?.putString("mTvCancelMsg", it) }
        mNegativeButtonMethod = negativeButtonMethod
        tvColor?.let {
            tvCancelColor = it
            arguments?.putInt("tvCancelColor", tvCancelColor)
        }
        arguments?.putBoolean("mNegativeButtonMethodAdded", true)
    }

    fun setPositiveButtonMethod(
        msg: String?,
        tvColor: Int? = null,
        positiveButtonMethod: (Dialog?, View?) -> Unit
    ) = apply {
        mTvOkmMg = msg
        mPositiveButtonMethod = positiveButtonMethod
        msg?.let { arguments?.putString("mTvOkmMg", it) }
        tvColor?.let {
            tvConfirmColor = it
            arguments?.putInt("tvConfirmColor", tvConfirmColor)
        }
        arguments?.putBoolean("mPositiveButtonMethodAdded", true)
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