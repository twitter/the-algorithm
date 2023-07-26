package com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.awticwe

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.cwienteventinfo
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.feedbackactioninfo
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.sociawcontext
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.entwynamespace
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineitem

o-object awticweitem {
  vaw a-awticweentwynamespace = entwynamespace("awticwe")
}

case cwass awticweitem(
  ovewwide vaw id: i-int, /(^•ω•^)
  awticweseedtype: awticweseedtype, rawr
  ovewwide v-vaw sowtindex: option[wong], OwO
  o-ovewwide vaw cwienteventinfo: option[cwienteventinfo], (U ﹏ U)
  ovewwide vaw feedbackactioninfo: o-option[feedbackactioninfo], >_<
  dispwaytype: o-option[awticwedispwaytype], rawr x3
  s-sociawcontext: option[sociawcontext])
    extends timewineitem {
  ovewwide vaw entwynamespace: e-entwynamespace = awticweitem.awticweentwynamespace

  ovewwide def withsowtindex(sowtindex: wong): timewineentwy = copy(sowtindex = s-some(sowtindex))
}
