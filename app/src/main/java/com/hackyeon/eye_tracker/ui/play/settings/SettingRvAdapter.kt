package com.hackyeon.eye_tracker.ui.play.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hackyeon.eye_tracker.databinding.SettingItemBinding
import com.hackyeon.eye_tracker.vo.SettingVO

class SettingRvAdapter: RecyclerView.Adapter<SettingRvAdapter.SettingViewHolder>() {

    private val asyncDiffer: AsyncListDiffer<SettingVO> = AsyncListDiffer<SettingVO>(this, SettingDiff)

    fun submitList(list: List<SettingVO>) = asyncDiffer.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val binding = SettingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SettingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = asyncDiffer.currentList.size

    inner class SettingViewHolder(private val binding: SettingItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }
}

object SettingDiff: DiffUtil.ItemCallback<SettingVO>() {
    override fun areItemsTheSame(oldItem: SettingVO, newItem: SettingVO): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SettingVO, newItem: SettingVO): Boolean {
        return oldItem == newItem
    }
}