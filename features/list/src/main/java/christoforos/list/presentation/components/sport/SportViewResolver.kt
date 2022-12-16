package christoforos.list.presentation.components.sport

import christoforos.list.domain.models.Event
import christoforos.list.domain.models.Sport

class SportViewResolver(private val view: SportViewInterface) {

    fun render(sport: Sport, onFavoriteClicked: (event: Event) -> Unit) {
        sport.description?.let { view.setTitle(it) }
        sport.events?.let { view.fillList(it) }
        view.setOnFavoriteClicked(onFavoriteClicked)
    }
}