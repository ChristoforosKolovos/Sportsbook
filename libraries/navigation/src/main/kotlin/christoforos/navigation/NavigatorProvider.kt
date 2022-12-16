package christoforos.navigation

import androidx.fragment.app.Fragment
import javax.inject.Inject

class NavigatorProvider @Inject constructor(
    var fragmentNavigator: FragmentNavigator
) {

    fun getNavigator(fragment: Fragment): Navigator {
        fragmentNavigator.fragment = fragment
        return fragmentNavigator
    }
}