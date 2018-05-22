package com.sazadgankar.syncplayer

import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_create_group.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_create_group.*
import android.widget.Toast.*


class CreateGroupFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity!!.title = "Create group"
        val view: View = inflater.inflate(R.layout.fragment_create_group, container, false)
        view.group_name_button.setOnClickListener {

            val gpName = group_name.text.toString()
            if (gpName == "") {
                Toast.makeText(activity, "Please enter a name for your group.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val groupName = group_name.text.toString()
            val newFragment = ControllerActivityFragment()
            val args = Bundle()
            args.putString("group_name", groupName)
            newFragment.arguments = args
            val transaction = activity!!.supportFragmentManager.beginTransaction()


            transaction.replace(R.id.fragment_container, newFragment)
            transaction.addToBackStack(null)

            // Commit the transaction
            transaction.commit()
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(getView()!!.windowToken, 0)
        }
        return view
    }

}