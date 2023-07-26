package com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.audio_space

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.cwienteventinfo
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.feedbackactioninfo
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.entwynamespace
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineitem

o-object audiospaceitem {
  v-vaw spaceentwynamespace = entwynamespace("audiospace")
}

case cwass audiospaceitem(
  ovewwide vaw id: stwing, -.-
  ovewwide v-vaw sowtindex: option[wong], ( ͡o ω ͡o )
  ovewwide vaw c-cwienteventinfo: option[cwienteventinfo], rawr x3
  o-ovewwide vaw feedbackactioninfo: option[feedbackactioninfo])
    extends t-timewineitem {
  ovewwide v-vaw entwynamespace: e-entwynamespace = audiospaceitem.spaceentwynamespace

  ovewwide def withsowtindex(sowtindex: wong): timewineentwy = c-copy(sowtindex = some(sowtindex))
}
