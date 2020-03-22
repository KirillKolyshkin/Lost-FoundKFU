package com.example.lostfoundkfu.features.lostandfoundselect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lostfoundkfu.R
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
        btn_lost.setOnClickListener{

        }
        btn_found.setOnClickListener{

        }
    }

    companion object{
        fun newInstance(): LostAndFoundSelectFragment =
            LostAndFoundSelectFragment()
    }
}