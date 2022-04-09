package com.example.getgif.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.getgif.R
import com.example.getgif.model.dataclasses.DataObject
import com.example.getgif.view.viewmodels.MainScreenViewModel
import com.example.getgif.view.adapters.GifAdapter
import com.google.android.material.snackbar.Snackbar

class MainScreenFragment : Fragment() {

    private lateinit var viewModel: MainScreenViewModel
    private lateinit var dataObjectsList : MutableList<DataObject>
    private lateinit var recyclerView : RecyclerView
    private lateinit var searchEditText : EditText
    private lateinit var searchImageViewButton : ImageView
    private lateinit var adapter : GifAdapter
    private var lastSearchString: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot: View = inflater.inflate(R.layout.main_screen_fragment, container, false)

        viewModel = ViewModelProvider(this).get(MainScreenViewModel::class.java)

        dataObjectsList = mutableListOf<DataObject>()
        adapter = GifAdapter(dataObjectsList)

        initUI(viewRoot)
        initObservers(viewRoot)

        return viewRoot
    }



    private fun initUI(viewRoot: View) {
        if(lastSearchString.equals(""))
        {
            viewModel.getTrendingGifs(viewRoot)
        }

        searchEditText = viewRoot.findViewById<EditText>(R.id.main_screen_search_editText)
        searchImageViewButton = viewRoot.findViewById<ImageView>(R.id.main_screen_search_imageViewButton)
        searchImageViewButton.setOnClickListener {
            val stringToSearch: String = searchEditText.text.toString()
            if(!lastSearchString.equals(stringToSearch))
            {
                if(!stringToSearch.equals(""))
                {
                    dataObjectsList.clear()
                    adapter.setList(dataObjectsList)
                    adapter.notifyDataSetChanged()
                    viewModel.getGifsByName(viewRoot, stringToSearch)
                }
                else
                {
                    dataObjectsList.clear()
                    adapter.setList(dataObjectsList)
                    adapter.notifyDataSetChanged()
                    viewModel.getTrendingGifs(viewRoot)
                }
            }
            lastSearchString = stringToSearch
        }

        recyclerView = viewRoot.findViewById<RecyclerView>(R.id.main_screen_recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.setHasFixedSize(true)

        adapter.setOnGifClickListener(object: GifAdapter.OnGifClickListener{
            override fun onGifClick(dataObject: DataObject) {
                val bundle: Bundle = Bundle()
                bundle.putString("url", dataObject.images.originalImage.url)

                NavHostFragment.findNavController(this@MainScreenFragment)
                    .navigate(R.id.action_mainScreenFragment_to_gifScreenFragment, bundle)
            }

        })
        recyclerView.adapter = adapter
    }

    private fun initObservers(viewRoot: View) {
        viewModel.gifsList.observe(viewLifecycleOwner, Observer { list ->
            dataObjectsList.clear()
            dataObjectsList.addAll(list)
            adapter.setList(dataObjectsList)
            adapter.notifyDataSetChanged()
           recyclerView.smoothScrollToPosition(0)
            if(list.isEmpty())
            {
                Snackbar.make(viewRoot, viewRoot.resources.getString(R.string.no_results), Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer { errorMsg ->
            Snackbar.make(viewRoot, errorMsg, Snackbar.LENGTH_LONG).show()
        })
    }
}