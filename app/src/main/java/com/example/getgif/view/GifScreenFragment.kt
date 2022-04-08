package com.example.getgif.view


import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.getgif.R
import com.google.android.material.snackbar.Snackbar
import java.io.File

class GifScreenFragment : Fragment() {


    private lateinit var viewModel: GifScreenViewModel
    private lateinit var gifImageView: ImageView
    private lateinit var shareButton: Button
    private lateinit var saveButton: Button
    private lateinit var url: String
    private lateinit var drawable: GifDrawable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot: View =  inflater.inflate(R.layout.gif_fragment, container, false)

        viewModel = ViewModelProvider(this).get(GifScreenViewModel::class.java)



        val bundle = arguments
        if(bundle != null)
        {
            if(bundle.getString("url") != null)
            {
                url = bundle.getString("url")!!
            }
        }

        initUI(viewRoot)

        return viewRoot
    }



    private fun initUI(viewRoot: View) {
        gifImageView = viewRoot.findViewById(R.id.gif_fragment_imageView)

        initImageViewAndDrawable(viewRoot)

        shareButton = viewRoot.findViewById(R.id.gif_fragment_share_gif_btn)
        shareButton.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                shareGif(viewRoot)
            }
        })

        saveButton = viewRoot.findViewById(R.id.gif_fragment_save_gif_btn)
        saveButton.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                saveGif(viewRoot)
            }
        })


    }

    private fun initImageViewAndDrawable(viewRoot: View) {
        Glide.with(viewRoot)
            .asGif()
            .load(url)
            .addListener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Snackbar.make(viewRoot, viewRoot.resources.getString(R.string.error_loading_gif), Snackbar.LENGTH_LONG).show()
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if(resource!= null)
                    {
                        drawable = resource
                    }

                    return false
                }
            })
            .into(gifImageView)
    }

    private fun shareGif(viewRoot: View) {
        val uri: Uri? = viewModel.getFileUriForGif(viewRoot, drawable)
        if(uri != null)
        {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/gif"

            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(shareIntent, resources.getString(R.string.share_via)))
        }
        else
        {
            Snackbar.make(viewRoot, viewRoot.resources.getString(R.string.error_sharing), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun saveGif(viewRoot: View) {
        val file: File? = viewModel.getFileForGif(viewRoot, drawable)
        if(file != null)
        {
            MediaScannerConnection.scanFile(context, arrayOf(file.toString()),
                null, null)
            Snackbar.make(viewRoot, viewRoot.resources.getString(R.string.download_finished), Snackbar.LENGTH_LONG).show()
        }
        else
        {
            Snackbar.make(viewRoot, viewRoot.resources.getString(R.string.error_saving), Snackbar.LENGTH_LONG).show()
        }
    }


}