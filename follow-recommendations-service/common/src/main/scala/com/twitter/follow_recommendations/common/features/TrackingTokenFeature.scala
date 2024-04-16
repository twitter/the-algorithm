package com.tw ter.follow_recom ndat ons.common.features

 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case object Track ngTokenFeature extends FeatureW hDefaultOnFa lure[P pel neQuery, Opt on[ nt]] {
  overr de val defaultValue: Opt on[ nt] = None
}
