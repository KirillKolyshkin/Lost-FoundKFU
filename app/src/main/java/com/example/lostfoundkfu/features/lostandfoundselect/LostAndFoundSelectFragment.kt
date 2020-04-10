package com.example.lostfoundkfu.features.lostandfoundselect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.features.createlostobject.CreateLostObjectFragment
import com.example.lostfoundkfu.features.mainscreen.MainActivity
import kotlinx.android.synthetic.main.lost_and_found_select_fragment.*

class LostAndFoundSelectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.lost_and_found_select_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_lost.setOnClickListener {
            (activity as MainActivity).openCreateNewLostObjectFragment()
        }
        btn_found.setOnClickListener {
            (activity as MainActivity).openCreateNewFoundObjectFragment()
        }
    }

    companion object {
        fun newInstance(): LostAndFoundSelectFragment =
            LostAndFoundSelectFragment()
    }
}
