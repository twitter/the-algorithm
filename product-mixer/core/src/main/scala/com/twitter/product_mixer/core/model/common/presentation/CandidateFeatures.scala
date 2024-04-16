package com.tw ter.product_m xer.core.model.common.presentat on

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport scala.collect on. mmutable.L stSet

/**
 * A l st set of all t  cand date p pel nes a cand date or g nated from. T   s typ cally a
 * s ngle ele nt set, but  rg ng cand dates across p pel nes us ng
 * [[com.tw ter.product_m xer.component_l brary.selector.Comb neFeatureMapsCand date rger]]
 * w ll  rge sets for t  cand date. T  last ele nt of t  set  s t  f rst p pel ne  dent f er
 * as   prepend new ones s nce   want O(1) access for t  last ele nt.
 */
object Cand dateP pel nes extends Feature[Un versalNoun[Any], L stSet[Cand dateP pel ne dent f er]]

/**
 * A l st set of all t  cand date s ces a cand date or g nated from. T   s typ cally a
 * s ngle ele nt set, but  rg ng cand dates across p pel nes us ng
 * [[com.tw ter.product_m xer.component_l brary.selector.Comb neFeatureMapsCand date rger]]
 * w ll  rge sets for t  cand date. T  last ele nt of t  set  s t  f rst s ce  dent f er
 * as   prepend new ones s nce   want O(1) access for t  last ele nt.
 */
object Cand dateS ces extends Feature[Un versalNoun[Any], L stSet[Cand dateS ce dent f er]]

/**
 * T  s ce pos  on relat ve to all cand dates t  or g nat ng cand date s ce a cand date
 * ca  from. W n  rged w h ot r cand dates, t  pos  on from t  f rst cand date s ce
 * takes pr or y.
 */
object Cand dateS cePos  on extends Feature[Un versalNoun[Any],  nt]
