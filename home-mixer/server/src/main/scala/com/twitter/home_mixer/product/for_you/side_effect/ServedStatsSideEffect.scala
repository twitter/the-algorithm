package com.tw ter.ho _m xer.product.for_ .s de_effect

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.Exper  ntStatsParam
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ServedStatsS deEffect @ nject() (statsRece ver: StatsRece ver)
    extends P pel neResultS deEffect[P pel neQuery, T  l ne] {

  overr de val  dent f er: S deEffect dent f er = S deEffect dent f er("ServedStats")

  pr vate val baseStatsRece ver = statsRece ver.scope( dent f er.toStr ng)
  pr vate val suggestTypeStatsRece ver = baseStatsRece ver.scope("SuggestType")
  pr vate val responseS zeStatsRece ver = baseStatsRece ver.scope("ResponseS ze")
  pr vate val contentBalanceStatsRece ver = baseStatsRece ver.scope("ContentBalance")

  pr vate val  nNetworkStatsRece ver = contentBalanceStatsRece ver.scope(" nNetwork")
  pr vate val outOfNetworkStatsRece ver = contentBalanceStatsRece ver.scope("OutOfNetwork")
  pr vate val replyStatsRece ver = contentBalanceStatsRece ver.scope("Reply")
  pr vate val or g nalStatsRece ver = contentBalanceStatsRece ver.scope("Or g nal")

  pr vate val emptyStatsRece ver = responseS zeStatsRece ver.scope("Empty")
  pr vate val lessThan5StatsRece ver = responseS zeStatsRece ver.scope("LessThan5")
  pr vate val lessThan10StatsRece ver = responseS zeStatsRece ver.scope("LessThan10")

  overr de def apply(
     nputs: P pel neResultS deEffect. nputs[P pel neQuery, T  l ne]
  ): St ch[Un ] = {
    val t etCand dates = Cand datesUt l
      .get emCand dates( nputs.selectedCand dates).f lter(_. sCand dateType[T etCand date]())

    val expBucket =  nputs.query.params(Exper  ntStatsParam)

    recordSuggestTypeStats(t etCand dates, expBucket)
    recordContentBalanceStats(t etCand dates, expBucket)
    recordResponseS zeStats(t etCand dates, expBucket)
    St ch.Un 
  }

  def recordSuggestTypeStats(
    cand dates: Seq[ emCand dateW hDeta ls],
    expBucket: Str ng
  ): Un  = {
    cand dates.groupBy(getSuggestType).foreach {
      case (suggestType, suggestTypeCand dates) =>
        suggestTypeStatsRece ver
          .scope(expBucket).counter(suggestType). ncr(suggestTypeCand dates.s ze)
    }
  }

  def recordContentBalanceStats(
    cand dates: Seq[ emCand dateW hDeta ls],
    expBucket: Str ng
  ): Un  = {
    val ( n, oon) = cand dates.part  on(_.features.getOrElse( nNetworkFeature, true))
     nNetworkStatsRece ver.counter(expBucket). ncr( n.s ze)
    outOfNetworkStatsRece ver.counter(expBucket). ncr(oon.s ze)

    val (reply, or g nal) =
      cand dates.part  on(_.features.getOrElse( nReplyToT et dFeature, None). sDef ned)
    replyStatsRece ver.counter(expBucket). ncr(reply.s ze)
    or g nalStatsRece ver.counter(expBucket). ncr(or g nal.s ze)
  }

  def recordResponseS zeStats(
    cand dates: Seq[ emCand dateW hDeta ls],
    expBucket: Str ng
  ): Un  = {
     f (cand dates.s ze == 0) emptyStatsRece ver.counter(expBucket). ncr()
     f (cand dates.s ze < 5) lessThan5StatsRece ver.counter(expBucket). ncr()
     f (cand dates.s ze < 10) lessThan10StatsRece ver.counter(expBucket). ncr()
  }

  pr vate def getSuggestType(cand date: Cand dateW hDeta ls): Str ng =
    cand date.features.getOrElse(SuggestTypeFeature, None).map(_.na ).getOrElse("None")
}
