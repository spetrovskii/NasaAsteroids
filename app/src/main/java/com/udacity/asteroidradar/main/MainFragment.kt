package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.asteroidRecycler.adapter = AsteroidsAdapter(AsteroidsAdapter.OnClickListener{
            viewModel.displayAsteroid(it)
        })

        val application = requireNotNull(this.activity).application
        val dataSource = AsteroidsDatabase.getInstance(application).asteroidDatabaseDao
        val viewModelFactory = AsteroidsViewModelFactory(dataSource, application)
        val asteroidsViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(AsteroidsViewModel::class.java)
        binding.asteroidsViewMoel = asteroidsViewModel

        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, Observer {
            if (it != null){
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidCompleted()
            }

        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
