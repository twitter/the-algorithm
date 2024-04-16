package com.tw ter.product_m xer.component_l brary.cand date_s ce.account_recom ndat ons_m xer

 mport com.tw ter.account_recom ndat ons_m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ceW hExtractedFeatures
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand datesW hS ceFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

object WhoToFollowModule aderFeature extends Feature[UserCand date, t. ader]
object WhoToFollowModuleFooterFeature extends Feature[UserCand date, Opt on[t.Footer]]
object WhoToFollowModuleD splayOpt onsFeature
    extends Feature[UserCand date, Opt on[t.D splayOpt ons]]

@S ngleton
class AccountRecom ndat onsM xerCand dateS ce @ nject() (
  accountRecom ndat onsM xer: t.AccountRecom ndat onsM xer. thodPerEndpo nt)
    extends Cand dateS ceW hExtractedFeatures[
      t.AccountRecom ndat onsM xerRequest,
      t.Recom ndedUser
    ] {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er(na  = "AccountRecom ndat onsM xer")

  overr de def apply(
    request: t.AccountRecom ndat onsM xerRequest
  ): St ch[Cand datesW hS ceFeatures[t.Recom ndedUser]] = {
    St ch
      .callFuture(accountRecom ndat onsM xer.getWtfRecom ndat ons(request))
      .map { response: t.WhoToFollowResponse =>
        responseToCand datesW hS ceFeatures(
          response.userRecom ndat ons,
          response. ader,
          response.footer,
          response.d splayOpt ons)
      }
  }

  pr vate def responseToCand datesW hS ceFeatures(
    userRecom ndat ons: Seq[t.Recom ndedUser],
     ader: t. ader,
    footer: Opt on[t.Footer],
    d splayOpt ons: Opt on[t.D splayOpt ons],
  ): Cand datesW hS ceFeatures[t.Recom ndedUser] = {
    val features = FeatureMapBu lder()
      .add(WhoToFollowModule aderFeature,  ader)
      .add(WhoToFollowModuleFooterFeature, footer)
      .add(WhoToFollowModuleD splayOpt onsFeature, d splayOpt ons)
      .bu ld()
    Cand datesW hS ceFeatures(userRecom ndat ons, features)
  }
}
