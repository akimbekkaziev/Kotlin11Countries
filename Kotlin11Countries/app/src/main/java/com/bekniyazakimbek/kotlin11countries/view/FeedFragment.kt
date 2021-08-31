package com.bekniyazakimbek.kotlin11countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bekniyazakimbek.kotlin11countries.adapter.CountryAdapter
import com.bekniyazakimbek.kotlin11countries.databinding.FragmentFeedBinding
import com.bekniyazakimbek.kotlin11countries.viewmodel.FeedViewModel
import kotlinx.coroutines.InternalCoroutinesApi

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class FeedFragment : Fragment() {
    private var _binding : FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FeedViewModel
    private val adapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this,this.defaultViewModelProviderFactory).get(FeedViewModel::class.java)
        viewModel.refreshData()

        binding.countryList.layoutManager = LinearLayoutManager(requireContext())
        binding.countryList.adapter = adapter

        observeData()
        binding.swipeRefresh.setOnRefreshListener {
            binding.countryProgressBar.visibility = View.VISIBLE
            binding.countryList.visibility = View.GONE
            binding.countryError.visibility = View.GONE
            viewModel.refreshAPI()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun observeData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries->
            countries?.let {
                adapter.updateCountryList(countries)
                binding.countryList.visibility = View.VISIBLE
                binding.countryError.visibility = View.GONE
                binding.countryProgressBar.visibility = View.GONE
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { countryLoading->
            countryLoading?.let {
                if(it){
                    binding.countryProgressBar.visibility = View.VISIBLE
                }else{
                    binding.countryProgressBar.visibility = View.GONE
                }
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(error){
                    binding.countryError.visibility = View.VISIBLE
                }else{
                    binding.countryError.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}