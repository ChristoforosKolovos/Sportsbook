package christoforos.favorites.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import christoforos.common.domain.models.event.Event
import christoforos.common.presentation.components.event.EventListAdapter
import christoforos.favorites.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import christoforos.common.R as CommonR
import christoforos.ui.R as UiR

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()
    private val eventListAdapter = EventListAdapter(
        matchParentWidth = true,
        onFavoriteClicked = ::favoriteClicked
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

        setupRecyclerView()
        setupViewListeners()
        setupObservers()
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
            findNavController().popBackStack()
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
        //todo
    }

    private fun renderNoResultsState() {
        //todo
    }

    private fun renderResultsState(events: List<Event>) {
        eventListAdapter.submitList(events)
    }

    private fun handleEffect(effect: FavoritesContract.Effect) {
        when (effect) {
            is FavoritesContract.Effect.ShowDialog -> showDialog(getString(effect.stringResourceId))
            is FavoritesContract.Effect.RemoveFavoriteFromList -> Unit //todo
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

}