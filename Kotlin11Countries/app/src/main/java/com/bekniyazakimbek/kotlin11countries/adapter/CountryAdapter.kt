package com.bekniyazakimbek.kotlin11countries.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bekniyazakimbek.kotlin11countries.databinding.CountryItemBinding
import com.bekniyazakimbek.kotlin11countries.model.Country
import com.bekniyazakimbek.kotlin11countries.util.downloadFromUrl
import com.bekniyazakimbek.kotlin11countries.util.placeholderProgressBar
import com.bekniyazakimbek.kotlin11countries.view.FeedFragmentDirections

class CountryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: CountryItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.countryItemName.text = countryList[position].countryName
        holder.binding.countryItemRegion.text = countryList[position].countryRegion
        holder.itemView.setOnClickListener {
            var action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.imageView3.downloadFromUrl(countryList[position].imageUrl,
            placeholderProgressBar(holder.itemView.context))
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(list: List<Country>){
        countryList.clear()
        countryList.addAll(list)
        notifyDataSetChanged()
    }
}