package christoforos.list.presentation

import androidx.lifecycle.viewModelScope
import christoforos.common.domain.interactors.favorites.RemoveFavoriteUseCase
import christoforos.common.domain.models.event.Event
import christoforos.common.domain.models.outcome.OutcomeExtensions.onError
import christoforos.common.domain.models.outcome.OutcomeExtensions.onSuccess
import christoforos.list.R
import christoforos.list.domain.interactors.AddFavoriteUseCase
import christoforos.list.domain.interactors.GetSportsUseCase
import christoforos.mvvmi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getSportsUseCase: GetSportsUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : BaseViewModel<ListContract.State, ListContract.Event, ListContract.Effect>(
    ListContract.State(emptyList(), ListContract.ScreenState.Initialize)
) {

    override suspend fun handleEvent(event: ListContract.Event) {
        when (event) {
            ListContract.Event.Initialize -> getSports()
            is ListContract.Event.OnFavoriteEvent -> handleFavoriteEventRequest(event.event)
            ListContract.Event.GetData -> getSports()
        }
    }

    private fun getSports() {
        setState {
            copy(
                sports = emptyList(),
                screenState = ListContract.ScreenState.Loading
            )
        }
        viewModelScope.launch {
            getSportsUseCase()
                .onSuccess {
                    it?.let {
                        setState {
                            copy(
                                sports = it,
                                screenState = ListContract.ScreenState.Results(it)
                            )
                        }
                    } ?: run {
                        setState {
                            copy(
                                sports = emptyList(),
                                screenState = ListContract.ScreenState.NoResults
                            )
                        }
                    }
                }.onError {
                    setState {
                        copy(
                            sports = emptyList(),
                            screenState = ListContract.ScreenState.Error
                        )
                    }
                }
        }
    }

    private fun handleFavoriteEventRequest(event: Event) {

        viewModelScope.launch {
            if (event.favorite) {
                addFavoriteUseCase(event)
                    .onSuccess {
                        setEffect { ListContract.Effect.AddedToFavorites }
                    }.onError {
                        setEffect { ListContract.Effect.ShowDialog(R.string.favorite_error) }
                    }
            } else {
                removeFavoriteUseCase(event)
                    .onError {
                        setEffect { ListContract.Effect.ShowDialog(R.string.favorite_remove_error) }
                    }
            }
        }
    }

}