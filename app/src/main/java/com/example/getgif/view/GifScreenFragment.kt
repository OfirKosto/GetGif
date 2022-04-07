package com.example.getgif.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.transition.Transition
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.example.getgif.R
import java.io.File

class GifScreenFragment : Fragment() {


    private lateinit var viewModel: GifScreenViewModel
    private lateinit var gifImageView: ImageView
    private lateinit var shareButton: Button
    private lateinit var saveButton: Button
    private lateinit var url: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot: View =  inflater.inflate(R.layout.gif_fragment, container, false)

        initUI(viewRoot)

        val bundle = arguments
        if(bundle != null)
        {
            if(bundle.getString("url") != null)
            {
                url = bundle.getString("url")!!
                Glide.with(viewRoot.context).load(url).into(gifImageView)
            }
        }





        return viewRoot
    }



    private fun initUI(viewRoot: View) {
        gifImageView = viewRoot.findViewById(R.id.gif_fragment_imageView)




        shareButton = viewRoot.findViewById(R.id.gif_fragment_share_gif_btn)
        shareButton.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
//                Glide.with(viewRoot).asBitmap().load(url)
//                    .apply(RequestOptions()).format(DecodeFormat.PREFER_ARGB_8888)
//                    .into(CustomTarget<Bitmap>() {
//                        @Override
//                        fun onResourceReady(resource: Bitmap , transition: Transition<? super Bitmap>) {
//
//                        }
//
//                        @Override
//                        fun onLoadCleared( placeholder: Drawable) {
//
//                        }
//                    })

            }
        })
        shareButton.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                shareImage()
            }

        })

        saveButton = viewRoot.findViewById(R.id.gif_fragment_save_gif_btn)
        saveButton.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun shareImage(){


        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, url)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(Intent.createChooser(intent, resources.getString(R.string.share_via)))
    }

}

//                Glide.with(viewRoot).asBitmap().load(url)
//                    .apply(RequestOptions()).format(DecodeFormat.PREFER_ARGB_8888)
//                    .into(CustomTarget<Bitmap>() {
//                        @Override
//                        fun onResourceReady(resource: Bitmap , transition: Transition<? super Bitmap>) {
//
//                        }
//
//                        @Override
//                        fun onLoadCleared( placeholder: Drawable) {
//
//                        }
//                    })

// contentUri: Uri?
//        val byteBuffer = (gifImageView.drawable as GifDrawable).buffer
//        val location = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "" + resources.getString(R.string.app_name))
//        val gifFile = File( location +"test.gif")
//
//        val output = FileOutputStream(gifFile)
//        val bytes = ByteArray(byteBuffer.capacity())
//        (byteBuffer.duplicate().clear() as ByteBuffer).get(bytes)
//        output.write(bytes, 0 ,bytes.size)
//        output.close()

//        val customTarget: CustomTarget<File> = CustomTarget<File>(222,222){
//            @Override
//            fun onResourceReady(resource: Bitmap , transition: Transition<File>) {
//
//            }
//
//            @Override
//            fun onLoadCleared( placeholder: Drawable) {
//
//            }
//        }