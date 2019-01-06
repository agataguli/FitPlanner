package com.who.helathy.fitplanner.adapter

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.domain.Sport

class SportAdapter: ArrayAdapter<Sport> {

    private var mContext: Context
    private var mSports: List<Sport> = ArrayList()

    constructor(@NonNull context: Context, @LayoutRes sports: ArrayList<Sport>): super(context, R.layout.item_sport, sports) {
        mContext = context
        mSports = sports
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem: View? = convertView
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_sport, parent, false)
        }

        val currentSport: Sport = mSports.get(position)
        val sportTv = listItem!!.findViewById(R.id.tv_sport) as TextView
        sportTv.text = currentSport.name
        val kcalTv = listItem!!.findViewById(R.id.tv_kcal) as TextView
        kcalTv.text = "${currentSport.lostKcalPerH} kcal/h"

        return listItem
    }

}