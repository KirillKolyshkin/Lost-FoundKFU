package com.example.lostfoundkfu.features.universallist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.data.Items.LostItem
import com.example.lostfoundkfu.data.db.UserProvider
import com.example.lostfoundkfu.features.App
import com.example.lostfoundkfu.features.mainscreen.MainActivity
import kotlinx.android.synthetic.main.universal_list_fragment.*
import javax.inject.Inject

class UniversalListFragment :
    MvpAppCompatFragment(),
    UniversalListView {

    @Inject
    @InjectPresenter
    lateinit var presenter: UniversalListPresenter

    @ProvidePresenter
    fun initPresenter() = presenter

    private val adapter = UniversalListAdapter { onItemClick(it) }

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
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.universal_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this.context, resources.configuration.orientation
            )
        )
        //presenter.getList()
        initList()
        initToolbar()
    }

    private fun initList(){
        val item = arguments?.getSerializable(ITEM) as LostItem?
        val userLink = arguments?.getString(USER_LINK)
        val case = arguments?.getInt(USE_CASE)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        if (case != null) {
            when (case) {
                0 -> userLink?.let{ presenter.getMyLostList(it)}
                1 -> userLink?.let{ presenter.getMyFoundList(it)}
                2 -> item?.let { presenter.getSupposedLostList(it) }
                3 -> item?.let { presenter.getSupposedFoundList(it) }
            }
        }
        presenter.setList()
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
        val case = arguments?.getInt(USE_CASE)
        if (case != null) {
            when (case) {
                0 -> toolbar.title = "Мой лист потерь"
                1 -> toolbar.title = "Мой лист находок"
                2 -> toolbar.title = "Предпологаемый лист находок"
                3 -> toolbar.title = "Предпологаемый лист потерь"
            }
        }

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

    fun getFilterList(textQuery: String) {
        adapter.list = ArrayList(dataList.filter { it.name.contains(textQuery) })
        adapter.notifyDataSetChanged()
    }

    override fun showList() {
        adapter.list = dataList
        adapter.notifyDataSetChanged()
    }

    override fun getList(items: ArrayList<LostItem>) {
        dataList = items
        showList()
    }

    companion object {
        var localMenu: Menu? = null
        var dataList = ArrayList<LostItem>()
        const val USE_CASE = "useCase"
        const val ITEM = "item"
        const val USER_LINK = "userLink"
        fun newInstance(aCase: UseCases, item: LostItem?, userLink: String?): UniversalListFragment {
            val args = Bundle()
            args.putInt(USE_CASE, aCase.ordinal)
            args.putSerializable(ITEM, item)
            args.putString(USER_LINK, userLink)
            val fragment = UniversalListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
