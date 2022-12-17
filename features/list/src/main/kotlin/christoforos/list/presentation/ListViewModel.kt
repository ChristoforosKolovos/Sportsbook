package christoforos.list.presentation

import androidx.lifecycle.viewModelScope
import christoforos.common.domain.models.outcome.OutcomeExtensions.onError
import christoforos.common.domain.models.outcome.OutcomeExtensions.onSuccess
import christoforos.list.domain.interactors.GetSportsUseCase
import christoforos.list.domain.models.Event
import christoforos.mvvmi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getSportsUseCase: GetSportsUseCase
) : BaseViewModel<ListContract.State, ListContract.Event, ListContract.Effect>(
    ListContract.State(emptyList(), ListContract.ScreenState.Loading)
) {

    init {
        getSports()
    }

    override suspend fun handleEvent(event: ListContract.Event) {
        when (event) {
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
        //todo
    }

}