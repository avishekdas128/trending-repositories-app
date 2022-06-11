package com.orangeink.trending.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orangeink.trending.databinding.RowRepositoryItemBinding
import com.orangeink.trending.data.local.model.Repository
import com.orangeink.trending.util.setData
import java.text.NumberFormat
import java.util.*

class RepositoryAdapter :
    ListAdapter<Repository, RepositoryAdapter.ListViewHolder>(RepositoryItemCallback) {

    var repository: List<Repository> = emptyList()
        set(value) {
            field = value
            onListOrFilterChange()
        }

    var filter: CharSequence = ""
        set(value) {
            field = value
            onListOrFilterChange()
        }

    object RepositoryItemCallback : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RowRepositoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        currentList[position].let {
            holder.bind(it)
        }
    }

    private fun onListOrFilterChange() {
        if (filter.isBlank()) {
            submitList(repository)
            return
        }
        val pattern = filter.toString().lowercase().trim()
        val filteredList = repository.filter { pattern in it.name.lowercase() }
        submitList(filteredList)
    }

    inner class ListViewHolder(private val binding: RowRepositoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repository) {
            binding.apply {
                binding.tvSelected.visibility = if (item.selected) View.VISIBLE else View.INVISIBLE
                binding.tvGrowthStars.text =
                    String.format("%d stars today", item.currentPeriodStars)
                val repoTitle = SpannableStringBuilder()
                    .append(item.author)
                    .append(" / ")
                    .bold { append(item.name) }
                binding.tvRepoTitle.text = repoTitle
                binding.tvRepoDesc.setData(item.description)
                binding.rvAuthors.apply {
                    layoutManager =
                        LinearLayoutManager(root.context, RecyclerView.HORIZONTAL, false)
                    setHasFixedSize(true)
                    adapter = AuthorAdapter(item.builtBy)
                }
                if (!item.language.isNullOrBlank()) {
                    binding.llLanguage.visibility = View.VISIBLE
                    binding.tvLanguageName.text = item.language
                    binding.tvLanguageColor.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(item.languageColor))
                } else binding.llLanguage.visibility = View.GONE
                binding.tvStars.text = NumberFormat.getNumberInstance(Locale.US).format(item.stars)
                binding.tvForks.text = NumberFormat.getNumberInstance(Locale.US).format(item.forks)
                itemView.setOnClickListener {
                    item.selected = true
                    notifyItemChanged(bindingAdapterPosition)
                }
            }
        }
    }

}