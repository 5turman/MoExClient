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
import org.example.moex.R
import org.example.moex.core.ResourceText
import org.example.moex.core.bind
import org.example.moex.data.model.Share
import org.example.moex.databinding.FragmentSharesBinding
import org.example.moex.ui.share.ShareActivity
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by 5turman on 22.03.2017.
 */
class SharesFragment : Fragment() {

    private val vm: SharesViewModel by viewModel()
    private lateinit var adapter: SharesAdapter

    private var searchView: SearchView? = null
    private var searchViewState: SearchViewState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = SharesAdapter(object : SharesAdapter.Callback {
            override fun onShareClicked(share: Share) {
                vm.onShareClicked(share)
            }
        })
        searchViewState = savedInstanceState?.getParcelable(STATE_SEARCH_VIEW)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSharesBinding.inflate(inflater, container, false).run {
        setupUI(this)
        setupBindings(this)
        root
    }

    override fun onDestroyView() {
        searchView = null
        super.onDestroyView()
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
                vm.onQueryTextChange(newText)
                return true
            }
        })

        searchViewState?.let {
            searchView.isIconified = it.isIconified
            searchView.setQuery(it.query, false)
        }

        this.searchView = searchView
    }

    private fun setupUI(binding: FragmentSharesBinding) {
        binding.apply {
            toolbar.setTitle(R.string.title_shares)
            (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

            refreshLayout.setOnRefreshListener { vm.onRefresh() }

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }
    }

    private fun setupBindings(binding: FragmentSharesBinding) {
        bind(vm.refreshing()) { binding.refreshLayout.isRefreshing = it }
        bind(vm.shares()) { shares ->
            adapter.setItems(shares)
            binding.zeroView.isVisible = shares.isEmpty()
        }
        bind(vm.commands()) { command ->
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

private const val STATE_SEARCH_VIEW = "search_view"
