package christoforos.favorites.presentation

import androidx.annotation.StringRes
import christoforos.mvvmi.UiEffect
import christoforos.mvvmi.UiEvent
import christoforos.mvvmi.UiState
import christoforos.common.domain.models.event.Event as SportEvent

object FavoritesContract {

    sealed interface Effect : UiEffect {
        data class ShowDialog(@StringRes val stringResourceId: Int) : Effect
        data class RemoveFavoriteFromList(val event: SportEvent) : Effect
    }

    sealed interface Event : UiEvent {
        object Initialize : Event
        data class FavoriteRemoved(val event: SportEvent) : Event
        object GetData : Event
        object AllItemsRemoved : Event
    }

    data class State(
        val screenState: ScreenState
    ) : UiState

    sealed interface ScreenState {
        object Initialize : ScreenState
        data class Results(val events: List<SportEvent>) : ScreenState
        object NoResults : ScreenState
        object Error : ScreenState
    }

}