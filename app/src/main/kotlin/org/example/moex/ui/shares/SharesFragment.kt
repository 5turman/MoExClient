package org.example.moex.ui.shares

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_shares.*
import org.example.moex.App
import org.example.moex.R
import org.example.moex.core.ResourceText
import org.example.moex.core.bind
import org.example.moex.core.getViewModel
import org.example.moex.data.model.Share
import org.example.moex.ui.share.ShareActivity

/**
 * Created by 5turman on 22.03.2017.
 */
class SharesFragment : Fragment() {

    private lateinit var viewModel: SharesViewModel
    private lateinit var adapter: SharesAdapter

    private var searchView: SearchView? = null
    private var searchViewState: SearchViewState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = App.component(requireContext()).sharesViewModelFactory()
        viewModel = getViewModel(factory)

        adapter = SharesAdapter(object : SharesAdapter.Callback {
            override fun onClick(position: Int) {
                val share = adapter.getItem(position)
                viewModel.onShareClick(share)
            }
        })
        searchViewState = savedInstanceState?.getParcelable(STATE_SEARCH_VIEW)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_shares, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
        setupBindings()
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
                viewModel.onQueryTextChange(newText)
                return true
            }
        })

        searchViewState?.let {
            searchView.isIconified = it.isIconified
            searchView.setQuery(it.query, false)
        }

        this.searchView = searchView
    }

    private fun setupUI() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            setTitle(R.string.title_shares)
        }

        refreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@SharesFragment.adapter
        }
    }

    private fun setupBindings() {
        bind(viewModel.refreshing()) { refreshLayout.isRefreshing = it }
        bind(viewModel.shares()) { shares ->
            adapter.setItems(shares)
            zeroView.isVisible = shares.isEmpty()
        }
        bind(viewModel.commands()) { command ->
            when (command) {
                is ShowError -> showError(command.error)
                is NavigateToShareScreen -> navigateToShareScreen(command.share)
                else -> throw IllegalStateException("Unsupported command: $command")
            }
        }
    }

    private fun showError(error: ResourceText) {
        Snackbar.make(view!!, error.invoke(requireContext()), Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToShareScreen(share: Share) {
        startActivity(ShareActivity.newIntent(requireContext(), share.id))
//        startActivity(ChartActivity.newIntent(context!!, share.id))
    }

}

const val STATE_SEARCH_VIEW = "search_view"
