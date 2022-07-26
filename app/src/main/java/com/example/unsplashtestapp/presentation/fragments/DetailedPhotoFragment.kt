package com.example.unsplashtestapp.presentation.fragments

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.unsplashtestapp.databinding.DetailedPhotoFragmentBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class DetailedPhotoFragment : BaseFragment<DetailedPhotoFragmentBinding>() {

    private var comeURL = ""

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DetailedPhotoFragmentBinding =
        DetailedPhotoFragmentBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        comeURL = requireArguments().getString(URL).toString()
        setImage()
        setOnClick()
    }

    private fun setImage() {
        Picasso.get().load(comeURL)
            .into(binding.detailedPhotoImage)
    }

    private fun setOnClick() {
        binding.wallpaperBtn.setOnClickListener {
            Picasso.get().load(comeURL).into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    setWallpaper(bitmap)
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setWallpaper(bitmap: Bitmap?) {
        val wallpaperManager = WallpaperManager.getInstance(context)
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                wallpaperManager.setBitmap(bitmap)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
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