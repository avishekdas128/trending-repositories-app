package com.orangeink.trending.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orangeink.trending.databinding.RowAuthorItemBinding
import com.orangeink.trending.data.local.model.BuiltBy
import com.orangeink.trending.util.loadImage

class AuthorAdapter(
    private val mList: List<BuiltBy>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RowAuthorItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mList[position].let {
            if (holder is ListViewHolder) holder.bind(it)
        }
    }

    inner class ListViewHolder(private val binding: RowAuthorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BuiltBy) {
            binding.apply {
                binding.ivAuthor.loadImage(item.avatar)
            }
        }
    }

}