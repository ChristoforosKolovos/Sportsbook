package christoforos.navigation

interface Navigator {

    fun navigate(target: Target, data: Any? = null)

}