package com.rigil.scenarios.common


import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.core.content.FileProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rigil.scenarios.R
import kotlinx.android.synthetic.main.dialog_video_view.*
import java.io.File

//TODO: to remove deprecated dialog fragment
class VideoViewFragment : DialogFragment() {
    private var fileUrl: String? = ""
    private var listener: VideoViewFragment.VideoPlaybackListener? = null
    private var isPlaying = true

    fun setFileUrlWithListener(filePath: String?, listener: VideoViewFragment.VideoPlaybackListener) {
        this.fileUrl = filePath
        this.listener = listener
    }

    fun setFileUrl(filePath: String?){
        this.fileUrl = filePath
    }

    interface VideoPlaybackListener {
        fun onDestroyVideoView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vv_video.setVideoURI(getVideoFileUri(fileUrl))
        vv_video.requestFocus()
        vv_video.setOnPreparedListener {
            vv_video.start()
        }
        vv_video.setOnErrorListener { _, _, _ ->
            listener?.onDestroyVideoView()
            this.dismiss()
            true
        }
        vv_video?.setOnCompletionListener {
            listener?.onDestroyVideoView()
            this.dismiss()
        }
        iv_cancel.setOnClickListener {
            listener?.onDestroyVideoView()
            this.dismiss()
        }
        iv_play_pause.setOnClickListener {
            isPlaying = if (isPlaying) {
                iv_play_pause.setImageResource(android.R.drawable.ic_media_play)
                vv_video.pause()
                false
            } else {
                vv_video.resume()
                iv_play_pause.setImageResource(android.R.drawable.ic_media_pause)
                true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window.setLayout(width, height)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(activity).inflate(R.layout.dialog_video_view, null)
    }

    private fun getVideoFileUri(fileUrl: String?): Uri? {
        if (fileUrl == null || activity == null) return null
        val fileName = fileUrl.split("/").last()
        val dirPath = activity?.getExternalFilesDir(null)?.path + File.separator
        val file = File(dirPath + fileName)
        try {
            return FileProvider.getUriForFile(activity,
                    activity.packageName + ".provider", file)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}