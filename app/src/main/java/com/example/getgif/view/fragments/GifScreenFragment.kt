package com.example.getgif.view.fragments


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.getgif.R
import com.example.getgif.view.viewmodels.GifScreenViewModel
import com.google.android.material.snackbar.Snackbar
import java.io.File

class GifScreenFragment : Fragment() {


    private lateinit var viewModel: GifScreenViewModel
    private lateinit var gifImageView: ImageView
    private lateinit var shareButton: Button
    private lateinit var saveButton: Button
    private lateinit var backButton: Button
    private lateinit var url: String
    private lateinit var drawable: GifDrawable
    private var requestPermissionLauncher: ActivityResultLauncher<String>? = null

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
        initLaunchers(viewRoot)
        initUI(viewRoot)

        if (ActivityCompat.checkSelfPermission(viewRoot.context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissionLauncher!!.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        return viewRoot
    }



    private fun initUI(viewRoot: View) {
        gifImageView = viewRoot.findViewById(R.id.gif_fragment_imageView)

        initImageViewAndDrawable(viewRoot)

        shareButton = viewRoot.findViewById(R.id.gif_fragment_share_gif_btn)
        shareButton.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if (ActivityCompat.checkSelfPermission(viewRoot.context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissionLauncher!!.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                else
                {
                    shareGif(viewRoot)
                }
            }
        })

        saveButton = viewRoot.findViewById(R.id.gif_fragment_save_gif_btn)
        saveButton.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if (ActivityCompat.checkSelfPermission(viewRoot.context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissionLauncher!!.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                else
                {
                    saveGif(viewRoot)
                }
            }
        })

        backButton = viewRoot.findViewById(R.id.favorites_articles_fragment_back_btn)
        backButton.setOnClickListener(View.OnClickListener { v: View? ->
            NavHostFragment.findNavController(this).popBackStack()
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
            shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
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


    private fun initLaunchers(viewRoot: View) {
        requestPermissionLauncher = registerForActivityResult(RequestPermission()) { result ->
            if (!result!!) {
                Snackbar.make(viewRoot, viewRoot.resources.getString(R.string.no_permissions), Snackbar.LENGTH_LONG).show()
            }
        }
    }

}