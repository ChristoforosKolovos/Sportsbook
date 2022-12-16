package christoforos.test.core

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * TestRunner is used in order Hilt to be able to provide the Application dependency in UI tests.
 * TestRunner must be declared in each build.gradle file that is needed.
 * defaultConfig {
 *     testInstrumentationRunner '(...).TestRunner'
 * }
 */

class TestRunner: AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}