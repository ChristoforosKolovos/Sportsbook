package christoforos.favorites.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import christoforos.common.domain.models.event.Event
import christoforos.common.presentation.components.event.EventListAdapter
import christoforos.favorites.databinding.FragmentFavoritesBinding
import christoforos.navigation.Navigator
import christoforos.navigation.NavigatorProvider
import christoforos.navigation.Target
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import christoforos.common.R as CommonR
import christoforos.ui.R as UiR

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    @Inject
    lateinit var navigatorProvider: NavigatorProvider
    private lateinit var navigator: Navigator
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()
    private val eventListAdapter = EventListAdapter(
        matchParentWidth = true,
        onFavoriteClicked = ::favoriteClicked,
        onAllItemsRemoved = ::onAllItemsRemoved
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigator = navigatorProvider.getNavigator(this)
        setUpBackNavigation()
        setupRecyclerView()
        setupViewListeners()
        setupObservers()
    }

    private fun setUpBackNavigation() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateToList()
                }
            })
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.resultsList
        val screenWidth = resources.displayMetrics.widthPixels
        val eventItemWidth = resources.getDimensionPixelSize(CommonR.dimen.event_container_width)
        val columns = screenWidth / eventItemWidth

        with(recyclerView) {
            layoutManager =
                GridLayoutManager(this.context, columns)
            adapter = eventListAdapter
        }
    }

    private fun setupViewListeners() {
        binding.back.setOnClickListener {
            navigateToList()
        }
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

    private fun handleScreenState(state: FavoritesContract.ScreenState) {
        when (state) {
            FavoritesContract.ScreenState.Initial -> Unit
            FavoritesContract.ScreenState.Error -> renderErrorState()
            FavoritesContract.ScreenState.NoResults -> renderNoResultsState()
            is FavoritesContract.ScreenState.Results -> renderResultsState(state.events)
        }
    }

    private fun renderErrorState() {
        with(binding) {
            resultsList.isVisible = false
            error.isVisible = true
            noResults.isVisible = false
        }
    }

    private fun renderNoResultsState() {
        with(binding) {
            resultsList.isVisible = false
            error.isVisible = false
            noResults.isVisible = true
        }
    }

    private fun renderResultsState(events: List<Event>) {
        with(binding) {
            resultsList.isVisible = true
            error.isVisible = false
            noResults.isVisible = false
        }
        eventListAdapter.submitList(events)
    }

    private fun handleEffect(effect: FavoritesContract.Effect) {
        when (effect) {
            is FavoritesContract.Effect.ShowDialog -> showDialog(getString(effect.stringResourceId))
            is FavoritesContract.Effect.RemoveFavoriteFromList -> eventListAdapter.remove(effect.event)
        }
    }

    private fun showDialog(text: String) {
        val safeContext: Context = this.context ?: return
        with(AlertDialog.Builder(safeContext)) {
            setMessage(text)
            setNeutralButton(UiR.string.ok, null)
            create()
            show()
        }
    }

    private fun favoriteClicked(event: Event) {
        viewModel.sendEvent(FavoritesContract.Event.FavoriteRemoved(event))
    }

    private fun onAllItemsRemoved() {
        viewModel.sendEvent(FavoritesContract.Event.AllItemsRemoved)
    }

    private fun navigateToList() {
        navigator.navigate(Target.List)
    }
}