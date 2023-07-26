package com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.icon_wabew

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.icon.howizonicon
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.cwienteventinfo
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.feedbackactioninfo
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.wichtext.wichtext
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.entwynamespace
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineitem

object iconwabewitem {
  v-vaw iconwabewentwynamespace = entwynamespace("iconwabew")
}

case cwass iconwabewitem(
  o-ovewwide vaw id: stwing, (U ﹏ U)
  o-ovewwide vaw sowtindex: option[wong], >_<
  ovewwide vaw cwienteventinfo: option[cwienteventinfo], rawr x3
  o-ovewwide vaw feedbackactioninfo: o-option[feedbackactioninfo], mya
  t-text: wichtext, nyaa~~
  icon: option[howizonicon])
    extends timewineitem {
  o-ovewwide vaw entwynamespace: entwynamespace = iconwabewitem.iconwabewentwynamespace

  ovewwide d-def withsowtindex(sowtindex: wong): timewineentwy = c-copy(sowtindex = s-some(sowtindex))
}
