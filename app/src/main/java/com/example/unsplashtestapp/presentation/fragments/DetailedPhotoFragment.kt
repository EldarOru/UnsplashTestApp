package com.example.unsplashtestapp.presentation.fragments

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.unsplashtestapp.databinding.DetailedPhotoFragmentBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception


class DetailedPhotoFragment: Fragment() {

    private lateinit var detailedPhotoFragmentBinding: DetailedPhotoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailedPhotoFragmentBinding = DetailedPhotoFragmentBinding.inflate(inflater, container, false)
        return detailedPhotoFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWallpaper()
        setOnClick()
    }

    private fun setWallpaper() {
        Picasso.get().load(requireArguments().getString(URL).toString())
            .into(detailedPhotoFragmentBinding.detailedPhotoImage)
    }

    private fun setOnClick(){
        detailedPhotoFragmentBinding.wallpaperBtn.setOnClickListener {
            Picasso.get().load(requireArguments().getString(URL).toString()).into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    val wallpaperManager = WallpaperManager.getInstance(context)
                    try {
                        lifecycleScope.launch(Dispatchers.IO) {
                            wallpaperManager.setBitmap(bitmap)
                        }
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                    }
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                }
            })
        }
    }

    companion object {
        fun newInstanceDetailedPhotoFragment(url: String): DetailedPhotoFragment {
            return DetailedPhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(URL, url)
                }
            }
        }
        private const val URL = "URL"
    }
}