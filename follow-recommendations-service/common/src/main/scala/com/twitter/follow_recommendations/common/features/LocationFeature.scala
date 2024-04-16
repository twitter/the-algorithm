package com.tw ter.follow_recom ndat ons.common.features

 mport com.tw ter.follow_recom ndat ons.common.models.GeohashAndCountryCode
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case object Locat onFeature
    extends FeatureW hDefaultOnFa lure[P pel neQuery, Opt on[GeohashAndCountryCode]] {
  overr de val defaultValue: Opt on[GeohashAndCountryCode] = None
}
