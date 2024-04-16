package com.tw ter.follow_recom ndat ons.common.cand date_s ces.geo

 mport com.google. nject.S ngleton
 mport com.google. nject.na .Na d
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.Cac dCand dateS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.StratoFetc rW hUn V ewS ce
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter.follow_recom ndat ons.common.models.AccountProof
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.Popular nGeoProof
 mport com.tw ter.follow_recom ndat ons.common.models.Reason
 mport com.tw ter. rm .pop_geo.thr ftscala.PopUsers nPlace
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.ut l.Durat on
 mport javax. nject. nject

@S ngleton
class BasePopGeoS ce @ nject() (
  @Na d(Gu ceNa dConstants.POP_USERS_ N_PLACE_FETCHER) fetc r: Fetc r[
    Str ng,
    Un ,
    PopUsers nPlace
  ]) extends StratoFetc rW hUn V ewS ce[Str ng, PopUsers nPlace](
      fetc r,
      BasePopGeoS ce. dent f er) {

  overr de def map(target: Str ng, cand dates: PopUsers nPlace): Seq[Cand dateUser] =
    BasePopGeoS ce.map(target, cand dates)
}

object BasePopGeoS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er("BasePopGeoS ce")
  val MaxResults = 200

  def map(target: Str ng, cand dates: PopUsers nPlace): Seq[Cand dateUser] =
    cand dates.popUsers.sortBy(-_.score).take(BasePopGeoS ce.MaxResults).v ew.map { cand date =>
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
}

@S ngleton
class PopGeoS ce @ nject() (basePopGeoS ce: BasePopGeoS ce, statsRece ver: StatsRece ver)
    extends Cac dCand dateS ce[Str ng, Cand dateUser](
      basePopGeoS ce,
      PopGeoS ce.MaxCac S ze,
      PopGeoS ce.Cac TTL,
      statsRece ver,
      PopGeoS ce. dent f er)

object PopGeoS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er("PopGeoS ce")
  val MaxCac S ze = 20000
  val Cac TTL: Durat on = 1.h s
}
