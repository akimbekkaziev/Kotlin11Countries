package com.bekniyazakimbek.kotlin11countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bekniyazakimbek.kotlin11countries.R
import com.bekniyazakimbek.kotlin11countries.databinding.FragmentCountryBinding
import com.bekniyazakimbek.kotlin11countries.util.downloadFromUrl
import com.bekniyazakimbek.kotlin11countries.util.placeholderProgressBar
import com.bekniyazakimbek.kotlin11countries.viewmodel.CountryViewModel


class CountryFragment : Fragment() {

    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CountryViewModel
    private var countryUuid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).uuid
        }

        viewModel = ViewModelProvider(this,this.defaultViewModelProviderFactory).get(CountryViewModel::class.java)
        viewModel.refreshData(countryUuid)

        observeData()
    }
    private fun observeData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country->
            country?.let {
                binding.countryFragmentName.text = country.countryName
                binding.countryFragmentCapital.text = country.countryCapital
                binding.countryFragmentCurrency.text = country.countryCurrency
                binding.countryFragmentRegion.text = country.countryRegion
                binding.countryFragmentLanguage.text = country.countryLanguage
                binding.imageView.downloadFromUrl(country.imageUrl, placeholderProgressBar(requireContext()))
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}