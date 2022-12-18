package christoforos.sportsbook

import androidx.navigation.fragment.findNavController
import christoforos.navigation.FragmentNavigator
import christoforos.navigation.Target

class AppFragmentNavigator : FragmentNavigator() {

    override fun navigate(target: Target) {
        fragment?.findNavController()?.apply {
            when (target) {
                Target.Favorites -> navigate(R.id.action_listFragment_to_favoritesFragment)
                Target.List -> navigate(R.id.action_favoritesFragment_to_listFragment)
            }
        }
    }

}

