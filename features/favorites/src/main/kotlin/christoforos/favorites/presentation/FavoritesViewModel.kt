package christoforos.favorites.presentation

import androidx.lifecycle.viewModelScope
import christoforos.common.domain.interactors.favorites.GetFavoritesUseCase
import christoforos.common.domain.interactors.favorites.RemoveFavoriteUseCase
import christoforos.common.domain.models.event.Event
import christoforos.common.domain.models.outcome.OutcomeExtensions.onError
import christoforos.common.domain.models.outcome.OutcomeExtensions.onSuccess
import christoforos.favorites.R
import christoforos.mvvmi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getSportsUseCase: GetFavoritesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : BaseViewModel<FavoritesContract.State, FavoritesContract.Event, FavoritesContract.Effect>(
    FavoritesContract.State(FavoritesContract.ScreenState.Initial)
) {

    init {
        getFavorites()
    }

    override suspend fun handleEvent(event: FavoritesContract.Event) {
        when (event) {
            is FavoritesContract.Event.FavoriteRemoved -> handleFavoriteRemoved(event.event)
            FavoritesContract.Event.GetData -> getFavorites()
        }
    }

    private fun getFavorites() {
        viewModelScope.launch {
            getSportsUseCase()
                .onSuccess {
                    it?.let {
                        setState {
                            copy(screenState = FavoritesContract.ScreenState.Results(it))
                        }
                    } ?: run {
                        setState { copy(screenState = FavoritesContract.ScreenState.NoResults) }
                    }
                }.onError {
                    setState { copy(screenState = FavoritesContract.ScreenState.Error) }
                }
        }
    }

    private fun handleFavoriteRemoved(event: Event) {
        viewModelScope.launch {
            removeFavoriteUseCase(event)
                .onSuccess {
                    setEffect { FavoritesContract.Effect.RemoveFavoriteFromList(event) }
                }.onError {
                    setEffect { FavoritesContract.Effect.ShowDialog(R.string.remove_favorite_error) }
                }
        }
    }

}