package christoforos.list.presentation.components.event

interface EventViewInterface {
    fun setTime(time: String)

    fun setNames(description: String)

    fun setOnFavoriteClicked(action: () -> Unit)

    fun setFavorite(favorite: Boolean)
}