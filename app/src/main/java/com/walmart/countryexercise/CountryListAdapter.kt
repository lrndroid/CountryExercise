package com.walmart.countryexercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.walmart.core.model.Country

class CountryListAdapter() : ListAdapter<Country, CountryListAdapter.CountryViewHolder>(CountryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(country: Country) {
            itemView.findViewById<TextView>(R.id.name).text = country.name + ", "
            itemView.findViewById<TextView>(R.id.capital).text = country.capital
            itemView.findViewById<TextView>(R.id.region).text = country.region
            itemView.findViewById<TextView>(R.id.code).text = country.code
        }
    }

    private class CountryDiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }
}