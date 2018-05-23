package com.sazadgankar.syncplayer
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_controller.view.*


class ControllerActivityFragment : Fragment() {

    private var joinedDevicesArray: ArrayList<String> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DevicesRecyclerViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity!!.title = arguments!!.getString("group_name")

        val view: View = inflater.inflate(R.layout.fragment_controller, container, false)
        //Recycler view
        viewManager = LinearLayoutManager(activity)
        joinedDevicesArray = arrayListOf("sara", "azad")
        viewAdapter = DevicesRecyclerViewAdapter(joinedDevicesArray)
        recyclerView = view.joined_devices_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        view.add_member_button.setOnClickListener {
            // replace new fragment
            val newFragment = ChooseMusicFragment()
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()

            val args = Bundle()
            args.putInt("number_of_members",viewAdapter.numberOfMembers)
            newFragment.arguments = args
        }

        return view
    }


}
