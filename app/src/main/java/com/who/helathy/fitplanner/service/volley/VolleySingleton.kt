package com.who.helathy.fitplanner.service.volley

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

import com.android.volley.VolleyLog.TAG

class VolleySingleton {

    private var mRequestQueue: RequestQueue? = null

    companion object {
        private var mInstance: VolleySingleton? = null
        private var mContext: Context? = null

        fun getInstance(context: Context): VolleySingleton? {
            if (mInstance == null) {
                mInstance = VolleySingleton(context)
            }
            return mInstance
        }
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        getRequestQueue()!!.add(req)
    }

    private constructor(context: Context) {
        mContext = context
        mRequestQueue = getRequestQueue()
        mRequestQueue!!.cache.clear()
    }

    private fun getRequestQueue(): RequestQueue? {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext!!.applicationContext)
        }
        return mRequestQueue
    }
}