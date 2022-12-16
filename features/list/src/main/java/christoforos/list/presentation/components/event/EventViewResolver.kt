package christoforos.list.presentation.components.event

import christoforos.list.domain.models.Event

class EventViewResolver(private val view: EventViewInterface) {

    fun render(
        event: Event,
        onFavoriteClicked: () -> Unit
    ) {
        event.timestamp?.let { view.setTime(it) }
        event.description?.let { view.setDescription(it) }
        view.setFavorite(event.favorite)
        view.setOnFavoriteClicked(onFavoriteClicked)
    }
}