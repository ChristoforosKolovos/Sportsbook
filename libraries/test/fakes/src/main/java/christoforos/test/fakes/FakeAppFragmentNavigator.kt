package christoforos.test.fakes

import christoforos.navigation.FragmentNavigator
import christoforos.navigation.Target

class FakeAppFragmentNavigator : FragmentNavigator() {
    override fun navigate(target: Target, data: Any?) {}
}