package christoforos.common.presentation.components.event

interface EventViewInterface {
    fun setTime(time: String)

    fun setUnknownTime(string: String)

    fun setFirstName(name: String)

    fun setSecondName(name: String)

    fun setOnFavoriteClicked(action: () -> Unit)

    fun setFavorite(favorite: Boolean)

    fun setMatchParentWidth(matchParentWidth: Boolean)
}