package christoforos.common.helpers

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import christoforos.common.utils.NumberConstants.ONE
import christoforos.common.utils.NumberConstants.ZERO

class CustomSnapHelper : LinearSnapHelper() {

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        val linearLayoutManager = layoutManager as? LinearLayoutManager
            ?: return super.findSnapView(layoutManager)

        return linearLayoutManager
            .takeIf { snapAllowed(it) }
            ?.run { super.findSnapView(layoutManager) }
    }

    private fun snapAllowed(linearLayoutManager: LinearLayoutManager): Boolean {
        val firstCompletelyVisibleItemPosition =
            linearLayoutManager.findFirstCompletelyVisibleItemPosition()
        val lastCompletelyVisibleItemPosition =
            linearLayoutManager.findLastCompletelyVisibleItemPosition()
        val firstItemPosition = ZERO
        val lastItemPosition = linearLayoutManager.itemCount - ONE

        //If the first and the last item is are not fully visible allow snapping
        return firstCompletelyVisibleItemPosition != firstItemPosition
                && lastCompletelyVisibleItemPosition != lastItemPosition
    }
}