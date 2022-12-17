package christoforos.list.presentation.components.event

import christoforos.ui.R

object EventIconUtils {

    fun sportIdToResourceId(eventId: String?) = eventIds[eventId]

    private val eventIds = hashMapOf(
        "AMFO" to R.drawable.amfo,
        "BADM" to R.drawable.badm,
        "BASE" to R.drawable.base,
        "BASK" to R.drawable.bask,
        "BOX" to R.drawable.box,
        "CHES" to R.drawable.ches,
        "CRIC" to R.drawable.cric,
        "CYCL" to R.drawable.cycl,
        "DART" to R.drawable.dart,
        "ESPS" to R.drawable.esps,
        "FOOT" to R.drawable.foot,
        "FRM" to R.drawable.frm,
        "FUTS" to R.drawable.futs,
        "GOLF" to R.drawable.golf,
        "HAND" to R.drawable.hand,
        "LARC" to R.drawable.larc,
        "MMA" to R.drawable.mma,
        "MOTO" to R.drawable.moto,
        "RUGL" to R.drawable.rugl,
        "RUGU" to R.drawable.rugu,
        "SNOO" to R.drawable.snoo,
        "SPED" to R.drawable.sped,
        "TABL" to R.drawable.tabl,
        "TENN" to R.drawable.ten,
        "VOLL" to R.drawable.voll,
        "ICEH" to R.drawable.iceh,
    )

}
