package org.example.moex.ui.shares

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_shares.*
import org.example.moex.App
import org.example.moex.R
import org.example.moex.core.MvpAppCompatFragment
import org.example.moex.data.model.Share
import org.example.moex.ui.chart.ChartActivity

/**
 * Created by 5turman on 22.03.2017.
 */
class SharesFragment : MvpAppCompatFragment(), SharesContract.View {

    @InjectPresenter
    lateinit var presenter: SharesPresenter

    private lateinit var adapter: SharesAdapter

    private var searchView: SearchView? = null
    private var searchViewState: SearchViewState? = null

    @ProvidePresenter
    fun providePresenter() = App.component(requireContext()).createSharesPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = SharesAdapter(object : SharesAdapter.Callback {
            override fun onClick(position: Int) {
                val share = adapter.getItem(position)
                presenter.onShareClick(share)
            }
        })
        searchViewState = savedInstanceState?.getParcelable(STATE_SEARCH_VIEW)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View =
            inflater.inflate(R.layout.fragment_shares, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            setTitle(R.string.title_shares)
        }

        refreshLayout.setOnRefreshListener {
            presenter.onRefresh()
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@SharesFragment.adapter
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        searchView?.let {
            val state = SearchViewState(it.isIconified, it.query.toString())
            outState.putParcelable(STATE_SEARCH_VIEW, state)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = false

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.onQueryTextChange(newText)
                return true
            }
        })

        searchViewState?.let {
            searchView.isIconified = it.isIconified
            searchView.setQuery(it.query, false)
        }

        this.searchView = searchView
    }

    override fun showRefreshing(show: Boolean) {
        refreshLayout.isRefreshing = show
    }

    override fun showShares(shares: List<Share>) {
        adapter.setItems(shares)
        zeroView.isVisible = shares.isEmpty()
    }

    override fun showError(error: String) {
        Snackbar.make(view!!, error, Snackbar.LENGTH_LONG).show()
    }

    override fun show(share: Share) {
//        startActivity(ShareActivity.newIntent(context, share.id))
        startActivity(ChartActivity.newIntent(context!!, share.id))
    }

}

const val STATE_SEARCH_VIEW = "search_view"
