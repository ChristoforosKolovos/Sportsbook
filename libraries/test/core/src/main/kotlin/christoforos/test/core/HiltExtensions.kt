package christoforos.test.core

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import christoforos.test.core.ui.HiltTestActivity

inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    fragmentFactory: FragmentFactory? = null,
    crossinline action: T.() -> Unit = {}
) {
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    )

    ActivityScenario.launch<HiltTestActivity>(mainActivityIntent).onActivity { activity ->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }
        val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            T::class.java.classLoader,
            T::class.java.name
        )
        fragment.arguments = fragmentArgs

        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()

        (fragment as T).action()
    }

}