package christoforos.list.presentation.components.event

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import christoforos.list.R
import christoforos.list.databinding.LayoutEventViewBinding
import christoforos.ui.R as UI_R

class EventView(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs), EventViewInterface {

    private val binding: LayoutEventViewBinding

    init {
        binding = LayoutEventViewBinding.inflate(LayoutInflater.from(context), this, true)

        with(context.resources) {
            val height = getDimension(R.dimen.event_container_height).toInt()
            val width = getDimension(R.dimen.event_container_width).toInt()
            val margin = getDimension(UI_R.dimen.spacing_2x).toInt()

            val lp = LinearLayout.LayoutParams(width, height)
            lp.setMargins(margin, margin, margin, margin)
            setBackgroundResource(christoforos.ui.R.color.colorAccent)
            layoutParams = lp
        }
    }

    override fun setTime(time: String) {
        binding.time.text = time
    }

    override fun setDescription(description: String) {
        binding.description.text = description
    }

    override fun setOnFavoriteClicked(action: () -> Unit) {
        binding.favorite.setOnClickListener {
            action()
        }
    }

    override fun setFavorite(favorite: Boolean) {
        with(binding.favorite) {
            if (favorite) {
                setImageResource(UI_R.drawable.favorite_selector)
            } else {
                setImageResource(UI_R.drawable.not_favorite_selector)
            }
        }
    }

}