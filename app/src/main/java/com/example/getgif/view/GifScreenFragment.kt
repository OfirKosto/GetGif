package com.example.getgif.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.getgif.R

class GifScreenFragment : Fragment() {


    private lateinit var viewModel: GifScreenViewModel
    private lateinit var gifImageView: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot: View =  inflater.inflate(R.layout.gif_fragment, container, false)

        initUI(viewRoot)



        return viewRoot
    }



    private fun initUI(viewRoot: View) {
        gifImageView = viewRoot.findViewById<ImageView>(R.id.gif_fragment_imageView)

        val bundle: Bundle? = arguments
        if(bundle != null)
        {
            Glide.with(viewRoot.context).load(bundle.getString("url")).into(gifImageView)
        }

    }

}