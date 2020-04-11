package com.example.lostfoundkfu.features.foundlist

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
import com.example.lostfoundkfu.data.db.UserProvider
import com.example.lostfoundkfu.features.App
import com.example.lostfoundkfu.features.mainscreen.MainActivity
import kotlinx.android.synthetic.main.found_item_list_fragment.*
import javax.inject.Inject

class FoundListFragment: MvpAppCompatFragment(),
    FoundListView,
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    @InjectPresenter
    lateinit var foundItemListPresenter: FoundListPresenter

    @ProvidePresenter
    fun initPresenter() = foundItemListPresenter

    private val foundItemListAdapter = FoundListAdapter { onItemClick(it) }

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
        return inflater.inflate(R.layout.found_item_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this.context, resources.configuration.orientation
            )
        )
        foundItemListPresenter.getFoundList()
        recycler_view.adapter = foundItemListAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        foundItemListPresenter.setFoundList()
        swipe_container.setColorSchemeResources(R.color.colorAccent)
        swipe_container.setOnRefreshListener(this)
        initToolbar()
    }

    private fun onItemClick(item: LostItem) {
        if (UserProvider.curUser?.screen_name == item.userLink)
            (activity as MainActivity).openDetailFragment(item, true)
        else
            (activity as MainActivity).openDetailFragment(item, false)
    }

    private fun initToolbar() {
        val activity = (activity as MainActivity)
        activity.setSupportActionBar(toolbar)
        toolbar.title = "Лист Находок"
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
        showFoundList(ArrayList(dataList.filter { it.name.contains(textQuery) }))

    override fun onRefresh() {
        foundItemListPresenter.setFoundList()
    }

    override fun showFoundList(dataList: ArrayList<LostItem>) {
        foundItemListAdapter.list = dataList
        foundItemListAdapter.notifyDataSetChanged()
        swipe_container.isRefreshing = false
    }

    override fun getFoundList(items: ArrayList<LostItem>) {
        dataList = items
    }

    companion object {
        var localMenu: Menu? = null
        var dataList = ArrayList<LostItem>()
        fun newInstance(): FoundListFragment =
            FoundListFragment()
    }
}
