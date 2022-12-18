package christoforos.navigation

sealed interface Target {

    object Favorites : Target
    object List : Target

}
