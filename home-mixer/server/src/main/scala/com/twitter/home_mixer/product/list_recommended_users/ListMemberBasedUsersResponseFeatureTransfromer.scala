package com.tw ter.ho _m xer.product.l st_recom nded_users

 mport com.tw ter. rm .cand date.{thr ftscala => t}
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.model.L stRecom ndedUsersFeatures.ScoreFeature
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er

object L st mberBasedUsersResponseFeatureTransfro r
    extends Cand dateFeatureTransfor r[t.Cand date] {

  overr de val  dent f er: Transfor r dent f er = Transfor r dent f er("L st mberBasedUsers")

  overr de val features: Set[Feature[_, _]] = Set(ScoreFeature)

  overr de def transform(cand date: t.Cand date): FeatureMap = FeatureMapBu lder()
    .add(ScoreFeature, cand date.score)
    .bu ld()
}
