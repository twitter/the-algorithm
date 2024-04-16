package com.tw ter.product_m xer.component_l brary.cand date_s ce.people_d scovery

 mport com.tw ter.peopled scovery.ap .{thr ftscala => t}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ceW hExtractedFeatures
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand datesW hS ceFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.UnexpectedCand dateResult
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.logg ng.Logg ng
 mport javax. nject. nject
 mport javax. nject.S ngleton

object WhoToFollowModule aderFeature extends Feature[UserCand date, t. ader]
object WhoToFollowModuleD splayOpt onsFeature
    extends Feature[UserCand date, Opt on[t.D splayOpt ons]]
object WhoToFollowModuleShowMoreFeature extends Feature[UserCand date, Opt on[t.ShowMore]]

@S ngleton
class PeopleD scoveryCand dateS ce @ nject() (
  peopleD scoveryServ ce: t.Thr ftPeopleD scoveryServ ce. thodPerEndpo nt)
    extends Cand dateS ceW hExtractedFeatures[t.GetModuleRequest, t.Recom ndedUser]
    w h Logg ng {

  overr de val  dent f er: Cand dateS ce dent f er =
    Cand dateS ce dent f er(na  = "PeopleD scovery")

  overr de def apply(
    request: t.GetModuleRequest
  ): St ch[Cand datesW hS ceFeatures[t.Recom ndedUser]] = {
    St ch
      .callFuture(peopleD scoveryServ ce.getModules(request))
      .map { response: t.GetModuleResponse =>
        // under t  assumpt on getModules returns a max mum of one module
        response.modules
          .collectF rst { module =>
            module.la t match {
              case t.La t.UserB oL st(la t) =>
                la tToCand datesW hS ceFeatures(
                  la t.userRecom ndat ons,
                  la t. ader,
                  la t.d splayOpt ons,
                  la t.showMore)
              case t.La t.UserT etCarousel(la t) =>
                la tToCand datesW hS ceFeatures(
                  la t.userRecom ndat ons,
                  la t. ader,
                  la t.d splayOpt ons,
                  la t.showMore)
            }
          }.getOrElse(throw P pel neFa lure(UnexpectedCand dateResult, "unexpected m ss ng module"))
      }
  }

  pr vate def la tToCand datesW hS ceFeatures(
    userRecom ndat ons: Seq[t.Recom ndedUser],
     ader: t. ader,
    d splayOpt ons: Opt on[t.D splayOpt ons],
    showMore: Opt on[t.ShowMore],
  ): Cand datesW hS ceFeatures[t.Recom ndedUser] = {
    val features = FeatureMapBu lder()
      .add(WhoToFollowModule aderFeature,  ader)
      .add(WhoToFollowModuleD splayOpt onsFeature, d splayOpt ons)
      .add(WhoToFollowModuleShowMoreFeature, showMore)
      .bu ld()
    Cand datesW hS ceFeatures(userRecom ndat ons, features)
  }
}
