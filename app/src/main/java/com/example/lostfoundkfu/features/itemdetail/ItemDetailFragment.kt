package com.example.lostfoundkfu.features.itemdetail

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.data.Items.BuildingWithoutFlag
import com.example.lostfoundkfu.data.Items.LostItem
import com.example.lostfoundkfu.features.App
import com.example.lostfoundkfu.features.mainscreen.MainActivity
import kotlinx.android.synthetic.main.item_detail_fragment.*
import java.text.SimpleDateFormat
import javax.inject.Inject

class ItemDetailFragment : MvpAppCompatFragment(),
    ItemDetailView {

    @Inject
    @InjectPresenter
    lateinit var presenter: ItemDetailPresenter

    @ProvidePresenter
    fun initPresenter() = presenter

    private val adapter = BuildingsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance
            .getAppComponent()
            .mainActivityComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this.context, resources.configuration.orientation
            )
        )
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        //presenter.getBuildings()
        initView()
        initToolbar()
        initClickListeners()
    }

    private fun initToolbar() {
        val activity = (activity as MainActivity)
        activity.setSupportActionBar(toolbar)
        toolbar.title = "Детали находки"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }

    private fun initView() {
        val item = arguments?.getSerializable(ITEM) as LostItem
        tv_item_name.text = item.name
        tv_description.text = item.description
        tv_date.text = SimpleDateFormat("dd/M/yyyy").format(item.date)
        Glide.with(context!!).load(item.imageUrl).into(iv_photo)
        getBuildings(mapToBuildings(item.place))
        if (item.place.count() == 0)
            tv_buildings_list.text = ""
        else
            tv_buildings_list.text = "Список зданий"
        val isMy = arguments?.getBoolean(IS_MY)
        if (isMy != null && isMy) {
            btn_debt.text = "Удалить элемент"
            tv_debt_approve.text = ""
        } else {
            btn_debt.text = "VK"
            tv_debt_approve.text = "Страница пользователя нашедшего пропажу"
        }
    }

    private fun mapToBuildings(list: List<String>): ArrayList<BuildingWithoutFlag> {
        val newlist = ArrayList<BuildingWithoutFlag>()
        for (item in list) {
            when (item) {
                "Двойка" -> newlist.add(
                    BuildingWithoutFlag(item,
                        context?.let { ContextCompat.getDrawable(it, R.drawable.ic_bag) })
                )
            }
        }
        return newlist
    }

    private fun initClickListeners() {
        val item = arguments?.getSerializable(ITEM) as LostItem
        val isMy = arguments?.getBoolean(IS_MY)
        if (isMy != null && isMy) {
            btn_debt.setOnClickListener {
                presenter.deleteItem(item)
                activity?.onBackPressed()
            }
        } else {
            btn_debt.setOnClickListener {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/" + item.userLink))
                startActivity(browserIntent)
            }
        }
    }

    override fun getBuildings(list: ArrayList<BuildingWithoutFlag>) {
        val buildingsWithFlag = ArrayList<BuildingWithoutFlag>()
        for (building in list)
            buildingsWithFlag.add(BuildingWithoutFlag(building.name, building.image))
        adapter.list = buildingsWithFlag
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val ITEM = "item"
        const val IS_MY = "isMy"
        fun newInstance(item: LostItem, isMy: Boolean): ItemDetailFragment {
            val args = Bundle()
            args.putSerializable(ITEM, item)
            args.putBoolean(IS_MY, isMy)
            val fragment = ItemDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
