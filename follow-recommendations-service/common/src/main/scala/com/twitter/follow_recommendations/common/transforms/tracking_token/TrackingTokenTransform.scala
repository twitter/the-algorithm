package com.tw ter.follow_recom ndat ons.common.transforms.track ng_token

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.Transform
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.Sess on
 mport com.tw ter.follow_recom ndat ons.common.models.Track ngToken
 mport com.tw ter. rm .constants.Algor hmFeedbackTokens.Algor hmToFeedbackTokenMap
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.logg ng.Logg ng

 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * T  transform adds t  track ng token for all cand dates
 * S nce t  happens  n t  sa  request,   use t  sa  trace- d for all cand dates
 * T re are no RPC calls  n t  transform so  's safe to cha n   w h `andT n` at t  end of
 * all ot r product-spec f c transforms
 */
@S ngleton
class Track ngTokenTransform @ nject() (baseStatsRece ver: StatsRece ver)
    extends Transform[HasD splayLocat on w h HasCl entContext, Cand dateUser]
    w h Logg ng {

  def prof leResults(
    target: HasD splayLocat on w h HasCl entContext,
    cand dates: Seq[Cand dateUser]
  ) = {
    //  tr cs to track # results per cand date s ce
    val stats = baseStatsRece ver.scope(target.d splayLocat on.toStr ng + "/f nal_results")
    stats.stat("total").add(cand dates.s ze)

    stats.counter(target.d splayLocat on.toStr ng). ncr()

    val flattenedCand dates: Seq[(Cand dateS ce dent f er, Cand dateUser)] = for {
      cand date <- cand dates
       dent f er <- cand date.getPr maryCand dateS ce
    } y eld ( dent f er, cand date)
    val cand datesGroupedByS ce: Map[Cand dateS ce dent f er, Seq[Cand dateUser]] =
      flattenedCand dates.groupBy(_._1).mapValues(_.map(_._2))
    cand datesGroupedByS ce map {
      case (s ce, cand dates) => stats.stat(s ce.na ).add(cand dates.s ze)
    }
  }

  overr de def transform(
    target: HasD splayLocat on w h HasCl entContext,
    cand dates: Seq[Cand dateUser]
  ): St ch[Seq[Cand dateUser]] = {
    prof leResults(target, cand dates)

    St ch.value(
      target.getOpt onalUser d
        .map { _ =>
          cand dates.map {
            cand date =>
              val token = So (Track ngToken(
                sess on d = Sess on.getSess on d,
                d splayLocat on = So (target.d splayLocat on),
                controllerData = None,
                algor hm d = cand date.userCand dateS ceDeta ls.flatMap(_.pr maryCand dateS ce
                  .flatMap {  dent f er =>
                    Algor hm.w hNa Opt( dent f er.na ).flatMap(Algor hmToFeedbackTokenMap.get)
                  })
              ))
              cand date.copy(track ngToken = token)
          }
        }.getOrElse(cand dates))

  }
}
