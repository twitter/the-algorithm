package com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.message

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.cawwback
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.cwienteventinfo
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.feedbackactioninfo
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.entwynamespace
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineitem

object messagepwomptitem {
  vaw messagepwomptentwynamespace = entwynamespace("messagepwompt")
}

case cwass m-messagepwomptitem(
  ovewwide vaw id: stwing,
  o-ovewwide vaw sowtindex: option[wong], /(^•ω•^)
  o-ovewwide vaw cwienteventinfo: option[cwienteventinfo], rawr
  ovewwide vaw feedbackactioninfo: o-option[feedbackactioninfo], OwO
  ovewwide vaw ispinned: o-option[boowean], (U ﹏ U)
  c-content: messagecontent, >_<
  impwessioncawwbacks: option[wist[cawwback]])
    extends timewineitem {
  ovewwide v-vaw entwynamespace: entwynamespace =
    messagepwomptitem.messagepwomptentwynamespace

  ovewwide def withsowtindex(sowtindex: wong): timewineentwy = copy(sowtindex = s-some(sowtindex))
}
