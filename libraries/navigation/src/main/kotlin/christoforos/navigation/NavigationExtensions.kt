package christoforos.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson

object NavigationExtensions {

    const val DATA_KEY = "nav_data"

    inline fun <reified T> Fragment.getNavigationData(): T? {
        val data = this.arguments?.getString(DATA_KEY)
        data?.let {
            return Gson().fromJson(data, T::class.java)
        } ?: return null
    }


    fun <T> T.toNavigationData() = Bundle().apply {
        putString(DATA_KEY, Gson().toJson(this@toNavigationData))
    }

}