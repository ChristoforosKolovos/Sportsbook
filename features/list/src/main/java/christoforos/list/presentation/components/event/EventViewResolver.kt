package christoforos.list.presentation.components.event

import christoforos.common.utils.StringConstants.DOTS
import christoforos.common.utils.StringConstants.SPACED_DASH
import christoforos.list.domain.models.Event

class EventViewResolver(
    private val view: EventViewInterface,
    private val event: Event,
    onFavoriteClicked: () -> Unit
) {

    init {
        view.setFavorite(event.favorite)
        view.setOnFavoriteClicked(onFavoriteClicked)
        setTime()
        setNames()
    }

    private fun setTime() {
        event.timestamp?.let {
            view.setTime(it)
        } ?: run {
            view.setUnknownTime(DOTS)
        }
    }

    private fun setNames() {
        event.description?.let {
            val names = it.split(SPACED_DASH)
            if (names.size > 1) {
                view.setFirstName(names[0])
                view.setSecondName(names[1])
            } else if (names.isNotEmpty()) {
                view.setFirstName(names[0])
                view.setSecondName(DOTS)
            } else {
                view.setFirstName(DOTS)
                view.setSecondName(DOTS)
            }
        } ?: run {
            view.setFirstName(DOTS)
            view.setSecondName(DOTS)
        }
    }

}