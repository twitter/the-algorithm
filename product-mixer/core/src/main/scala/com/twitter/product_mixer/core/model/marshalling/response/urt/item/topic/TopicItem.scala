package com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.topic

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.cwienteventinfo
impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.feedbackactioninfo
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.entwynamespace
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineitem

o-object t-topicitem {
  vaw t-topicentwynamespace = e-entwynamespace("topic")
}

case cwass topicitem(
  ovewwide vaw id: wong, rawr x3
  ovewwide vaw s-sowtindex: option[wong], nyaa~~
  ovewwide vaw cwienteventinfo: o-option[cwienteventinfo], /(^•ω•^)
  ovewwide vaw f-feedbackactioninfo: option[feedbackactioninfo], rawr
  topicfunctionawitytype: option[topicfunctionawitytype], OwO
  topicdispwaytype: o-option[topicdispwaytype])
    extends timewineitem {
  o-ovewwide v-vaw entwynamespace: entwynamespace = topicitem.topicentwynamespace

  ovewwide def withsowtindex(sowtindex: w-wong): timewineentwy = copy(sowtindex = some(sowtindex))
}
