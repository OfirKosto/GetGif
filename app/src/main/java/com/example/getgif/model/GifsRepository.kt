package com.example.getgif.model

import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.core.content.FileProvider
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.example.getgif.BuildConfig
import com.example.getgif.R
import com.example.getgif.model.util.GifApiUtil
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.util.*

object GifsRepository {

    suspend fun getTrendingGifs() = GifApiUtil.getTrendingGifs()

    suspend fun getGifsByName(nameToSearch: String) = GifApiUtil.getGifsByName(nameToSearch)


    fun getFileUriForGif(viewRoot: View, gifDrawable: GifDrawable?) :Uri? {
        if(gifDrawable != null)
        {
            val baseDir: String = Environment.getExternalStorageDirectory().getAbsolutePath()
            val fileName =  "sharingGif.gif"

            val sharingGifFile = File(baseDir, fileName)
            gifDrawableToFile(gifDrawable, sharingGifFile);

            val uri: Uri = FileProvider.getUriForFile(
                viewRoot.context!!, BuildConfig.APPLICATION_ID + ".provider",
                sharingGifFile
            )

            return uri
        }
        else
        {
            return null
        }
    }

    fun getFileForGif(viewRoot: View, gifDrawable: GifDrawable?) :File? {
        if(gifDrawable != null)
        {
            val directory = File(Environment.getExternalStorageDirectory(), "/" +
                    viewRoot.resources.getString(R.string.app_name))

            if(!directory.exists())
            {
                if(!directory.mkdir())
                {
                    return null
                }
            }

            val fileName = Calendar.getInstance().timeInMillis.toString() + ".gif"

            val sharingGifFile = File(directory, fileName)
            gifDrawableToFile(gifDrawable, sharingGifFile)

            return sharingGifFile
        }
        else
        {
            return null
        }
    }

    private fun gifDrawableToFile(gifDrawable: GifDrawable, gifFile: File) {
        try{
            val byteBuffer = gifDrawable.buffer
            val output = FileOutputStream(gifFile)
            val bytes = ByteArray(byteBuffer.capacity())
            (byteBuffer.duplicate().clear() as ByteBuffer).get(bytes)
            output.write(bytes, 0, bytes.size)
            output.close()
            }
        catch (ex: FileNotFoundException)
        {
            ex.printStackTrace();
        }
        catch (ex: IOException)
        {
           ex.printStackTrace();
        }
    }

}