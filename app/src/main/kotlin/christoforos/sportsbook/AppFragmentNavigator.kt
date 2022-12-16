package christoforos.sportsbook

import androidx.navigation.fragment.findNavController
import christoforos.navigation.FragmentNavigator
import christoforos.navigation.Target

class AppFragmentNavigator : FragmentNavigator() {

    override fun navigate(target: Target, data: Any?) {
        fragment?.findNavController()?.apply {
            when (target) {
                else -> {}
            }
        }
    }

}

