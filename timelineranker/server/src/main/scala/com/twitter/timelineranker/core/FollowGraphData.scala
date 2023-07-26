package com.twittew.timewinewankew.cowe

impowt com.twittew.timewines.modew.usewid

/**
 * f-fowwow g-gwaph detaiws of a-a given usew. rawr x3 i-incwudes usews fowwowed, (U ﹏ U) b-but awso f-fowwowed usews i-in vawious
 * states o-of mute. (U ﹏ U)
 *
 * @pawam usewid id of a given usew. (⑅˘꒳˘)
 * @pawam fowwowedusewids i-ids of usews who the given usew fowwows. òωó
 * @pawam m-mutuawwyfowwowingusewids a subset o-of fowwowedusewids whewe fowwowed usews fowwow back the given u-usew. ʘwʘ
 * @pawam mutedusewids a-a subset of fowwowedusewids t-that the given usew has muted. /(^•ω•^)
 * @pawam wetweetsmutedusewids a subset o-of fowwowedusewids whose wetweets awe muted by the given usew. ʘwʘ
 */
case cwass f-fowwowgwaphdata(
  usewid: usewid, σωσ
  f-fowwowedusewids: s-seq[usewid], OwO
  m-mutuawwyfowwowingusewids: s-set[usewid], 😳😳😳
  mutedusewids: set[usewid], 😳😳😳
  wetweetsmutedusewids: s-set[usewid]) {
  vaw fiwtewedfowwowedusewids: seq[usewid] = fowwowedusewids.fiwtewnot(mutedusewids)
  v-vaw awwusewids: seq[usewid] = fiwtewedfowwowedusewids :+ usewid
  vaw innetwowkusewids: seq[usewid] = fowwowedusewids :+ usewid
}

object f-fowwowgwaphdata {
  vaw empty: f-fowwowgwaphdata = f-fowwowgwaphdata(
    0w, o.O
    s-seq.empty[usewid], ( ͡o ω ͡o )
    set.empty[usewid], (U ﹏ U)
    set.empty[usewid], (///ˬ///✿)
    set.empty[usewid]
  )
}
