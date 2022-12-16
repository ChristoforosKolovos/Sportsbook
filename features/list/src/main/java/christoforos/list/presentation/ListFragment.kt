package christoforos.list.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import christoforos.list.databinding.FragmentListBinding
import christoforos.list.domain.models.Sport
import christoforos.list.presentation.components.sport.SportListAdapter
import christoforos.navigation.Navigator
import christoforos.navigation.NavigatorProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import christoforos.ui.R as UI_R

@AndroidEntryPoint
class ListFragment : Fragment() {

    @Inject
    lateinit var navigatorProvider: NavigatorProvider
    private lateinit var binding: FragmentListBinding
    private lateinit var navigator: Navigator
    private val viewModel: ListViewModel by viewModels()
    private lateinit var sportListAdapter: SportListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigator = navigatorProvider.getNavigator(this)

        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        setupRecyclerViews()
    }

    private fun setupRecyclerViews() {
        val recyclerView = binding.resultsList
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        sportListAdapter = SportListAdapter {
            viewModel.sendEvent(ListContract.Event.OnFavoriteEvent(it))
        }
        recyclerView.adapter = sportListAdapter
    }

    private fun setupObservers() {
        viewModel.state()
            .map { it.screenState }
            .onEach(::handleScreenState)
            .launchIn(lifecycleScope)
        viewModel.effect()
            .onEach(::handleEffect)
            .launchIn(lifecycleScope)
    }

    private fun handleScreenState(state: ListContract.ScreenState) {
        when (state) {
            ListContract.ScreenState.Error -> renderErrorState()
            ListContract.ScreenState.Loading -> renderLoadingState()
            ListContract.ScreenState.NoResults -> renderNoResultsState()
            is ListContract.ScreenState.Results -> renderResultsState(state.sports)
        }
    }

    private fun handleEffect(effect: ListContract.Effect) {
        when (effect) {
            is ListContract.Effect.ShowDialog -> showDialog(getString(effect.stringResourceId))
        }
    }

    private fun renderErrorState() {
        with(binding) {
        }
    }

    private fun renderLoadingState() {
        with(binding) {
        }
    }

    private fun renderNoResultsState() {
        with(binding) {
        }
    }

    private fun renderResultsState(sports: List<Sport>) {
        with(binding) {
        }
        sportListAdapter.submitList(sports)
    }

    private fun showDialog(text: String) {
        val safeContext: Context = this.context ?: return
        with(AlertDialog.Builder(safeContext)) {
            setMessage(text)
            setNeutralButton(UI_R.string.ok, null)
            create()
            show()
        }
    }

}