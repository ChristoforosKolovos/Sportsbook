package christoforos.list.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
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

    companion object {
        private const val ALPHA_FULL = 1f
        private const val ALPHA_NO = 0f
        private const val SHOW_ANIM_DURATION = 300L
        private const val SHOW_ANIM_TRANSLATION_Y_START = -100f
        private const val SHOW_ANIM_TRANSLATION_Y_END = -0f
    }

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

        setupRecyclerViews()
        setupSwipeToRefresh()
        setupViewListeners()
        setupObservers()
    }

    private fun setupViewListeners() {
        with(binding) {
            favorites.setOnClickListener {
                //todo
            }
            retry.setOnClickListener {
                viewModel.sendEvent(ListContract.Event.GetData)
            }
        }
    }

    private fun setupSwipeToRefresh() {
        with(binding.swipeContainer) {
            setOnRefreshListener {
                viewModel.sendEvent(ListContract.Event.GetData)
            }
            setColorSchemeResources(UI_R.color.dark)
        }
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
            retry.showWithAnimation()
            error.showWithAnimation()
            noResults.isVisible = false
            resultsList.isVisible = false
            swipeContainer.isRefreshing = false
        }
    }

    private fun renderLoadingState() {
        with(binding) {
            retry.isVisible = false
            error.isVisible = false
            noResults.isVisible = false
            resultsList.isVisible = false
            swipeContainer.isRefreshing = true
        }
    }

    private fun renderNoResultsState() {
        with(binding) {
            retry.showWithAnimation()
            error.isVisible = false
            noResults.showWithAnimation()
            resultsList.isVisible = false
            swipeContainer.isRefreshing = false

        }
    }

    private fun renderResultsState(sports: List<Sport>) {
        with(binding) {
            retry.isVisible = false
            error.isVisible = false
            noResults.isVisible = false
            resultsList.showWithAnimation()
            swipeContainer.isRefreshing = false
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

    private fun View.showWithAnimation() {
        isVisible = true
        alpha = ALPHA_NO
        translationY = SHOW_ANIM_TRANSLATION_Y_START
        animate()
            .alpha(ALPHA_FULL)
            .translationY(SHOW_ANIM_TRANSLATION_Y_END)
            .setDuration(SHOW_ANIM_DURATION).start()
    }

}