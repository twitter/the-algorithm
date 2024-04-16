package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module

 mport com.tw ter.adserver.{thr ftscala => ad}
 mport com.tw ter. rm .{thr ftscala => h}
 mport com.tw ter.peopled scovery.ap .{thr ftscala => t}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er

object Ad mpress onFeature extends Feature[UserCand date, Opt on[ad.Ad mpress on]]
object  rm ContextTypeFeature extends Feature[UserCand date, Opt on[h.ContextType]]
object Soc alTextFeature extends Feature[UserCand date, Opt on[Str ng]]
object Track ngTokenFeature extends Feature[UserCand date, Opt on[Str ng]]
object ScoreFeature extends Feature[UserCand date, Opt on[Double]]

object WhoToFollowResponseFeatureTransfor r
    extends Cand dateFeatureTransfor r[t.Recom ndedUser] {

  overr de val  dent f er: Transfor r dent f er = Transfor r dent f er("WhoToFollowResponse")

  overr de val features: Set[Feature[_, _]] =
    Set(
      Ad mpress onFeature,
       rm ContextTypeFeature,
      Soc alTextFeature,
      Track ngTokenFeature,
      ScoreFeature)

  overr de def transform( nput: t.Recom ndedUser): FeatureMap = FeatureMapBu lder()
    .add(Ad mpress onFeature,  nput.ad mpress on)
    .add( rm ContextTypeFeature,  nput.reason.flatMap(_.contextType))
    .add(Soc alTextFeature,  nput.soc alText)
    .add(Track ngTokenFeature,  nput.track ngToken)
    .add(ScoreFeature,  nput.mlPred ct onScore)
    .bu ld()
}
