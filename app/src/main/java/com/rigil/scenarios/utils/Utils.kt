package com.rigil.scenarios.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.FileProvider
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rigil.mogcorelib.common.database.GameDao_Impl
import com.rigil.mogcorelib.common.database.GameDatabase
import com.rigil.mogcorelib.utils.loadGlideImage
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.net.URL


// loads image from db with given fileName
fun loadImageFromDb(fileName: String?, imageView: ImageView, context: Context) {
    if (fileName == null) {
        imageView.visibility = View.GONE
        return
    }
    imageView.visibility = View.VISIBLE
    val gameDao = GameDao_Impl(GameDatabase.getInstance(context))
    val imageUrlId = fileName.split("/").last()
    if (gameDao.blobDataCount(imageUrlId).blockingFirst() > 0) {
        val byteArray = gameDao.getBlobData(imageUrlId)
                .subscribeOn(Schedulers.io())
                .blockingFirst().file
        Glide.with(imageView)
                .load(byteArray)
                .into(imageView)
    } else {
        Glide.with(imageView).clear(imageView)
        imageView.loadGlideImage(fileName)
    }
}


fun String?.isItVideoFile(): Boolean {
    return (this?.contains(".mp4") ?: false || this?.contains(".mov") ?: false)
}

fun String.isValidUrl(): Boolean {
    return try {
        URL(this).toURI()
        true
    } catch (e: Exception) {
        false
    }
}

// loads video image thumbnail image at 100 milliseconds
fun loadVideoImageThumbnail(context: Context, imageView: ImageView, filePath: String?) {
    if (filePath == null) {
        imageView.visibility = View.GONE
        return
    }
    imageView.visibility = View.VISIBLE
    val fileName = filePath.split("/").last()
    val dirPath = imageView.context.getExternalFilesDir(null).path + File.separator
    val file = File(dirPath + fileName)
    val uri: Uri = FileProvider.getUriForFile(imageView.context,
            imageView.context.packageName + ".provider", file)
    val interval = 100
    val options = RequestOptions().frame(interval.toLong())
    Glide.with(context).asBitmap()
            .load(uri)
            .apply(options)
            .into(imageView)
}


