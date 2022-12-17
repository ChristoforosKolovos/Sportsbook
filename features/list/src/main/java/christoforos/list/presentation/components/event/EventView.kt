package christoforos.list.presentation.components.event

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import christoforos.common.utils.DateFormatConstants.TIME_FORMAT
import christoforos.common.utils.StringConstants.SPACED_DASH
import christoforos.list.R
import christoforos.list.databinding.LayoutEventViewBinding
import java.text.SimpleDateFormat
import java.util.*
import christoforos.ui.R as UI_R

class EventView(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs), EventViewInterface {

    private val binding: LayoutEventViewBinding
    private var timer: CountDownTimer? = null

    init {
        binding = LayoutEventViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun setTime(time: String) {
        binding.time.isEnabled = true

        val formatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        val millisUntilEvent = getMillisUntilTime(time.toLong())

        timer?.cancel()
        timer = object : CountDownTimer(millisUntilEvent, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeFormatted = formatter.format(millisUntilFinished)
                binding.time.text = timeFormatted
            }

            override fun onFinish() {
                binding.time.text = context.getString(R.string.finished)
                binding.time.isEnabled = false
            }
        }.start()
    }

    private fun getMillisUntilTime(time: Long): Long {
        val currentTime = (System.currentTimeMillis() / 1000)
        return time - currentTime
    }

    override fun setNames(description: String) {
        val names = description.split(SPACED_DASH)

        with(binding) {
            if (names.size > 1) {
                name1.text = names[0]
                name2.text = names[1]
            } else if (names.isNotEmpty()) {
                name1.text = names[0]
                name2.text = context.resources.getString(R.string.dots)
            } else {
                name1.text = context.resources.getString(R.string.dots)
                name2.text = context.resources.getString(R.string.dots)
            }
        }
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