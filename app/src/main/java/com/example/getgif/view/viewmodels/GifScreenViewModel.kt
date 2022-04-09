package com.example.getgif.view.viewmodels

import android.net.Uri
import android.view.View
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.example.getgif.model.repositories.GifsRepository

class GifScreenViewModel : ViewModel() {

    fun getFileUriForGif(viewRoot: View, gifDrawable: GifDrawable?) : Uri? =
        GifsRepository.getFileUriForGif(viewRoot, gifDrawable)

    fun getFileForGif(viewRoot: View, gifDrawable: GifDrawable?) = GifsRepository.getFileForGif(viewRoot, gifDrawable)

}