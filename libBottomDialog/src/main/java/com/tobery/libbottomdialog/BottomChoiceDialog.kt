package com.tobery.libbottomdialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * 仿IOS底部选择框
 */
class BottomChoiceDialog(
    private val data: List<String>,
    private val itemClickListener: (position: Int, value: String) -> Unit
) : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setGravity(Gravity.BOTTOM)
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val t = manager.beginTransaction()
        val f = manager.findFragmentByTag(tag)
        if (f != null) {
            t.remove(f)
        }
        t.commit()
        super.show(manager, tag)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvCancel = view.findViewById<TextView>(R.id.tv_dialog_cancel)
        val rvDialog = view.findViewById<RecyclerView>(R.id.rv_dialog)
        tvCancel.setOnClickListener { dismiss() }
        val divider = DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL)
        AppCompatResources.getDrawable(view.context, R.drawable.shape_dialog_divider)
            ?.let { divider.setDrawable(it) }
        rvDialog.addItemDecoration(divider)
        rvDialog.adapter = DialogAdapter(data) { position, value ->
            itemClickListener.invoke(position, value)
            dismiss()
        }
    }

}

class DialogAdapter(
    private val data: List<String>,
    private val itemClickListener: (position: Int, value: String) -> Unit
) : RecyclerView.Adapter<DialogAdapter.ItemVH>() {

    inner class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvItem = itemView.findViewById<TextView>(R.id.tv_item)
        fun bind(name: String, position: Int) {
            tvItem.text = name
            itemView.setOnClickListener {
                itemClickListener.invoke(position, name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        return ItemVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_bottom_dialog, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        holder.bind(data.get(position), position)
    }

    override fun getItemCount(): Int = data.size

}