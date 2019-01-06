package com.who.helathy.fitplanner.service.volley

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.Response;

class VolleyRequestProcessor {
    companion object {
        private val TAG = "VolleyRequestProcessor"

        fun processImageViewRequest(iv: ImageView, url: String, context: Context) {
            val imageRequest = ImageRequest(url, Response.Listener<Bitmap>() { it ->
                iv.setImageBitmap(it)
            }, 700, 700, null, null, Response.ErrorListener { _ ->
                Log.w(TAG, "Failed, image with url: $url not response")
            })
            VolleySingleton.getInstance(context)!!.addToRequestQueue(imageRequest)
        }
    }
}