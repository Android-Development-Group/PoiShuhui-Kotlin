package com.flying.xiaopo.poishuhui_kotlin.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flying.xiaopo.poishuhui_kotlin.Level
import com.flying.xiaopo.poishuhui_kotlin.R
import com.flying.xiaopo.poishuhui_kotlin.domain.model.NewsContainer
import com.flying.xiaopo.poishuhui_kotlin.domain.network.NewsSource
import com.flying.xiaopo.poishuhui_kotlin.kits.recycler.AnotherAdapter
import com.flying.xiaopo.poishuhui_kotlin.log
import com.flying.xiaopo.poishuhui_kotlin.ui.binder.NewsContainerBinder
import kotlinx.android.synthetic.main.fragment_news.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

/**
 * Third Page
 * @author wupanjie
 */
class NewsFragment : Fragment() {
    companion object {
        val AIM_URL = "http://ishuhui.net/CMS/"
    }

    var mData = ArrayList<NewsContainer>()

    lateinit var newsList: RecyclerView

    lateinit var newsRefresh: SwipeRefreshLayout

    lateinit var adapter: AnotherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState == null) log(Level.D, "savedInstanceState==null")
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) log(Level.D, "savedInstanceState==null")

        newsRefresh = view.newsRefresh
        newsList = view.newsList

        val layoutManager = LinearLayoutManager(context)
        layoutManager.initialPrefetchItemCount = 2
        newsList.layoutManager = layoutManager
        adapter = AnotherAdapter().with(NewsContainer::class.java, NewsContainerBinder())
        newsList.adapter = adapter

        newsRefresh.setOnRefreshListener {
            load()
        }
        newsRefresh.post { newsRefresh.isRefreshing = true }
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && mData.size == 0) {

            load()
        }

    }

    private fun load() {
        doAsync {
            val data = NewsSource().obtain(AIM_URL)
            uiThread {
                mData = data
                adapter.update(data)
                newsRefresh.isRefreshing = false
            }
        }
    }

}