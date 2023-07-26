package com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.tombstone

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.tweet.tweetitem
impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.cwienteventinfo
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.feedbackactioninfo
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.entwynamespace
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineitem

o-object tombstoneitem {
  vaw tombstoneentwynamespace = entwynamespace("tombstone")
}

case cwass tombstoneitem(
  ovewwide v-vaw id: wong, OwO
  ovewwide vaw sowtindex: option[wong], (U ﹏ U)
  o-ovewwide vaw cwienteventinfo: o-option[cwienteventinfo], >_<
  ovewwide vaw feedbackactioninfo: option[feedbackactioninfo], rawr x3
  t-tombstonedispwaytype: tombstonedispwaytype, mya
  t-tombstoneinfo: o-option[tombstoneinfo], nyaa~~
  tweet: option[tweetitem])
    extends timewineitem {
  ovewwide vaw entwynamespace: e-entwynamespace = tombstoneitem.tombstoneentwynamespace

  ovewwide def withsowtindex(sowtindex: wong): t-timewineentwy = copy(sowtindex = s-some(sowtindex))
}
