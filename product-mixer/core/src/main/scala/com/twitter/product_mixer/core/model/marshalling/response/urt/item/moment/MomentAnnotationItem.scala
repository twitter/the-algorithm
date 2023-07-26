package com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.moment

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.entwynamespace
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineitem
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.cwienteventinfo
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.feedbackactioninfo
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.wichtext.wichtext

object momentannotationitem {
  vaw momentannotationentwynamespace = entwynamespace("momentannotation")
}

/**
 * w-wepwesents a momentannotation uwt item. (˘ω˘)
 * this i-is pwimawiwy used by twends s-seawth wesuwt page fow dispwaying twends titwe ow descwiption
 * u-uwt api wefewence: https://docbiwd.twittew.biz/unified_wich_timewines_uwt/gen/com/twittew/timewines/wendew/thwiftscawa/momentannotation.htmw
 */
c-case cwass momentannotationitem(
  o-ovewwide vaw id: wong, (⑅˘꒳˘)
  ovewwide vaw sowtindex: option[wong],
  ovewwide vaw c-cwienteventinfo: option[cwienteventinfo], (///ˬ///✿)
  ovewwide vaw feedbackactioninfo: option[feedbackactioninfo], 😳😳😳
  ovewwide v-vaw ispinned: option[boowean], 🥺
  t-text: option[wichtext], mya
  h-headew: option[wichtext],
) e-extends t-timewineitem {

  ovewwide vaw entwynamespace: e-entwynamespace =
    momentannotationitem.momentannotationentwynamespace

  ovewwide def withsowtindex(sowtindex: w-wong): timewineentwy = copy(sowtindex = some(sowtindex))
}
