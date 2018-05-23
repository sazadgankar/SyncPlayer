package com.sazadgankar.syncplayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ChooseMusicFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val numberOfMembers = arguments!!.getInt("number_of_members")
        (activity as AppCompatActivity).supportActionBar!!.subtitle = "$numberOfMembers members"


        val view: View = inflater.inflate(R.layout.fragment_choose_music, container, false)




        return view
    }

}