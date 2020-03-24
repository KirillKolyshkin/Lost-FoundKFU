package com.example.lostfoundkfu.features.lostlist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.data.Items.LostItem
import com.example.lostfoundkfu.features.App
import com.example.lostfoundkfu.features.mainscreen.MainActivity
import kotlinx.android.synthetic.main.lost_item_list_fragment.*
import javax.inject.Inject

class LostListFragment: MvpAppCompatFragment(),
    LostListView,
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    @InjectPresenter
    lateinit var lostItemListPresenter: LostListPresenter

    @ProvidePresenter
    fun initPresenter() = lostItemListPresenter

    private val lostItemListAdapter = LostListAdapter { onItemClick(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance
            .getAppComponent()
            .mainActivityComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.lost_item_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this.context, resources.configuration.orientation
            )
        )
        lostItemListPresenter.getLostList()
        recycler_view.adapter = lostItemListAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        lostItemListPresenter.setLostList()
        swipe_container.setColorSchemeResources(R.color.colorAccent)
        swipe_container.setOnRefreshListener(this)
        initToolbar()
    }

    private fun onItemClick(item: LostItem) {

    }

    private fun initToolbar() {
        val activity = (activity as MainActivity)
        activity.setSupportActionBar(toolbar)
        toolbar.title = "Лист Потерь"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val activity = (activity as MainActivity)
        activity.menuInflater.inflate(R.menu.toolbar_items, menu)
        val mSearch = menu?.findItem(R.id.action_search)
        val mSearchView = mSearch?.actionView as SearchView
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getFilterList(newText)
                return false
            }
        })
        localMenu = menu
    }

    fun getFilterList(textQuery: String) =
        showLostList(ArrayList(dataList.filter { it.name.contains(textQuery) }))

    override fun onRefresh() {
        lostItemListPresenter.setLostList()
    }

    override fun showLostList(dataList: ArrayList<LostItem>) {
        lostItemListAdapter.list = dataList
        lostItemListAdapter.notifyDataSetChanged()
        swipe_container.isRefreshing = false
    }

    override fun getLostList(items: ArrayList<LostItem>) {
        dataList = items
    }

    companion object {
        var localMenu: Menu? = null
        var dataList = ArrayList<LostItem>()
        fun newInstance(): LostListFragment =
            LostListFragment()
    }
}