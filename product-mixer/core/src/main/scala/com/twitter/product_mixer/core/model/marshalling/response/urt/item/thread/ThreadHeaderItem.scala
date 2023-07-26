package com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.thwead

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.entwynamespace
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineitem
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.cwienteventinfo
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.feedbackactioninfo

o-object thweadheadewitem {
  vaw thweadheadewentwynamespace = entwynamespace("thweadheadew")
}

case cwass t-thweadheadewitem(
  ovewwide vaw id: wong, >_<
  o-ovewwide vaw sowtindex: option[wong], rawr x3
  o-ovewwide vaw cwienteventinfo: option[cwienteventinfo], mya
  ovewwide vaw f-feedbackactioninfo: option[feedbackactioninfo], nyaa~~
  o-ovewwide vaw ispinned: o-option[boowean], (⑅˘꒳˘)
  content: thweadheadewcontent)
    extends timewineitem {
  o-ovewwide vaw entwynamespace: entwynamespace = thweadheadewitem.thweadheadewentwynamespace

  ovewwide def w-withsowtindex(sowtindex: wong): t-timewineentwy = c-copy(sowtindex = s-some(sowtindex))
}
