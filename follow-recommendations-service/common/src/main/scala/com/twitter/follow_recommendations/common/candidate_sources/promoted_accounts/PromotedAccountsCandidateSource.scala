package com.tw ter.follow_recom ndat ons.common.cand date_s ces.promoted_accounts

 mport com.tw ter.adserver.thr ftscala.AdServerExcept on
 mport com.tw ter.adserver.{thr ftscala => adthr ft}
 mport com.tw ter.f nagle.T  outExcept on
 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cl ents.adserver.AdRequest
 mport com.tw ter.follow_recom ndat ons.common.cl ents.adserver.AdserverCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph.Soc alGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.models.FollowProof
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter. nject.Logg ng
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

case class PromotedCand dateUser(
   d: Long,
  pos  on:  nt,
  ad mpress on: adthr ft.Ad mpress on,
  followProof: FollowProof,
  pr maryCand dateS ce: Opt on[Cand dateS ce dent f er])

@S ngleton
class PromotedAccountsCand dateS ce @ nject() (
  adserverCl ent: AdserverCl ent,
  sgsCl ent: Soc alGraphCl ent,
  statsRece ver: StatsRece ver)
    extends Cand dateS ce[AdRequest, PromotedCand dateUser]
    w h Logg ng {

  overr de val  dent f er: Cand dateS ce dent f er =
    PromotedAccountsCand dateS ce. dent f er

  val stats: StatsRece ver = statsRece ver.scope( dent f er.na )
  val fa lureStat: StatsRece ver = stats.scope("fa lures")
  val adServerExcept onsCounter: Counter = fa lureStat.counter("AdServerExcept on")
  val t  outCounter: Counter = fa lureStat.counter("T  outExcept on")

  def apply(request: AdRequest): St ch[Seq[PromotedCand dateUser]] = {
    adserverCl ent
      .getAd mpress ons(request)
      .rescue {
        case e: T  outExcept on =>
          t  outCounter. ncr()
          logger.warn("T  out on Adserver", e)
          St ch.N l
        case e: AdServerExcept on =>
          adServerExcept onsCounter. ncr()
          logger.warn("Fa led to fetch ads", e)
          St ch.N l
      }
      .flatMap { ad mpress ons: Seq[adthr ft.Ad mpress on] =>
        prof leNumResults(ad mpress ons.s ze, "results_from_ad_server")
        val  dTo mpMap = (for {
           mp <- ad mpress ons
          promotedAccount d <-  mp.promotedAccount d
        } y eld promotedAccount d ->  mp).toMap
        request.cl entContext.user d
          .map { user d =>
            sgsCl ent
              .get ntersect ons(
                user d,
                ad mpress ons.f lter(shouldShowSoc alContext).flatMap(_.promotedAccount d),
                PromotedAccountsCand dateS ce.Num ntersect ons
              ).map { promotedAccountW h ntersect ons =>
                 dTo mpMap.map {
                  case (promotedAccount d,  mp) =>
                    PromotedCand dateUser(
                      promotedAccount d,
                       mp. nsert onPos  on
                        .map(_.to nt).getOrElse(
                          get nsert onPos  onDefaultValue(request. sTest.getOrElse(false))
                        ),
                       mp,
                      promotedAccountW h ntersect ons
                        .getOrElse(promotedAccount d, FollowProof(N l, 0)),
                      So ( dent f er)
                    )
                }.toSeq
              }.onSuccess(result => prof leNumResults(result.s ze, "f nal_results"))
          }.getOrElse(St ch.N l)
      }
  }

  pr vate def shouldShowSoc alContext( mp: adthr ft.Ad mpress on): Boolean =
     mp.exper  ntValues.ex sts { expValues =>
      expValues.get("d splay.d splay_style").conta ns("show_soc al_context")
    }

  pr vate def get nsert onPos  onDefaultValue( sTest: Boolean):  nt = {
     f ( sTest) 0 else -1
  }

  pr vate def prof leNumResults(resultsS ze:  nt, statNa : Str ng): Un  = {
     f (resultsS ze <= 5) {
      stats.scope(statNa ).counter(resultsS ze.toStr ng). ncr()
    } else {
      stats.scope(statNa ).counter("more_than_5"). ncr()
    }
  }
}

object PromotedAccountsCand dateS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.PromotedAccount.toStr ng)
  val Num ntersect ons = 3
}
