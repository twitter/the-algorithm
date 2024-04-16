package com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo
 mport com.google. nject.S ngleton
 mport com.tw ter.esc rb rd.ut l.st chcac .St chCac 
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.AccountProof
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.Popular nGeoProof
 mport com.tw ter.follow_recom ndat ons.common.models.Reason
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter. rm .pop_geo.thr ftscala.PopUsers nPlace
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.Un quePopQual yFollowUsers nPlaceCl entColumn
 mport com.tw ter.ut l.Durat on
 mport javax. nject. nject

@S ngleton
class PopGeohashQual yFollowS ce @ nject() (
  popGeoS ce: PopGeoQual yFollowS ce,
  statsRece ver: StatsRece ver)
    extends BasePopGeohashS ce(
      popGeoS ce = popGeoS ce,
      statsRece ver = statsRece ver.scope("PopGeohashQual yFollowS ce"),
    ) {
  overr de val  dent f er: Cand dateS ce dent f er = PopGeohashQual yFollowS ce. dent f er
  overr de def maxResults(target: Target):  nt = {
    target.params(PopGeoQual yFollowS ceParams.PopGeoS ceMaxResultsPerPrec s on)
  }
  overr de def m nGeohashLength(target: Target):  nt = {
    target.params(PopGeoQual yFollowS ceParams.PopGeoS ceGeoHashM nPrec s on)
  }
  overr de def maxGeohashLength(target: Target):  nt = {
    target.params(PopGeoQual yFollowS ceParams.PopGeoS ceGeoHashMaxPrec s on)
  }
  overr de def returnResultFromAllPrec s on(target: Target): Boolean = {
    target.params(PopGeoQual yFollowS ceParams.PopGeoS ceReturnFromAllPrec s ons)
  }
  overr de def cand dateS ceEnabled(target: Target): Boolean = {
    target.params(PopGeoQual yFollowS ceParams.Cand dateS ceEnabled)
  }
}

object PopGeohashQual yFollowS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.PopGeohashQual yFollow.toStr ng)
}

object PopGeoQual yFollowS ce {
  val MaxCac S ze = 20000
  val Cac TTL: Durat on = Durat on.fromH s(24)
  val MaxResults = 200
}

@S ngleton
class PopGeoQual yFollowS ce @ nject() (
  popGeoQual yFollowCl entColumn: Un quePopQual yFollowUsers nPlaceCl entColumn,
  statsRece ver: StatsRece ver,
) extends Cand dateS ce[Str ng, Cand dateUser] {

  /** @see [[Cand dateS ce dent f er]] */
  overr de val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    "PopGeoQual yFollowS ce")

  pr vate val cac  = St chCac [Str ng, Opt on[PopUsers nPlace]](
    maxCac S ze = PopGeoQual yFollowS ce.MaxCac S ze,
    ttl = PopGeoQual yFollowS ce.Cac TTL,
    statsRece ver = statsRece ver.scope( dent f er.na , "cac "),
    underly ngCall = (k: Str ng) => {
      popGeoQual yFollowCl entColumn.fetc r
        .fetch(k)
        .map { result => result.v }
    }
  )

  overr de def apply(target: Str ng): St ch[Seq[Cand dateUser]] = {
    val result: St ch[Opt on[PopUsers nPlace]] = cac .readThrough(target)
    result.map { pu =>
      pu.map { cand dates =>
          cand dates.popUsers.sortBy(-_.score).take(PopGeoQual yFollowS ce.MaxResults).map {
            cand date =>
              Cand dateUser(
                 d = cand date.user d,
                score = So (cand date.score),
                reason = So (
                  Reason(
                    So (
                      AccountProof(
                        popular nGeoProof = So (Popular nGeoProof(locat on = cand dates.place))
                      )
                    )
                  )
                )
              )
          }
        }.getOrElse(N l)
    }
  }
}
