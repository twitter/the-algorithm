package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateUt l
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.ut l.Future

object OONT etNegat veFeedbackBasedPred cate {

  def ntabD sl keBasedPred cate(
  )(
     mpl c  stats: StatsRece ver
  ): Na dPred cate[
    PushCand date w h T etCand date w h Recom ndat onType
  ] = {
    val na  = "oon_t et_d sl ke_based_pred cate"
    val scopedStatsRece ver = stats.scope(na )
    val allOonCand datesCounter = scopedStatsRece ver.counter("all_oon_cand dates")
    val oonCand dates mpressedCounter =
      scopedStatsRece ver.counter("oon_cand dates_ mpressed")
    val f lteredCand datesCounter =
      scopedStatsRece ver.counter("f ltered_oon_cand dates")

    val ntabD sl keCountFeature =
      "t et.mag c_recs_t et_real_t  _aggregates_v2.pa r.v2.mag crecs.realt  . s_ntab_d sl ked.any_feature.Durat on.Top.count"
    val sentFeature =
      "t et.mag c_recs_t et_real_t  _aggregates_v2.pa r.v2.mag crecs.realt  . s_sent.any_feature.Durat on.Top.count"

    Pred cate
      .fromAsync { cand date: PushCand date w h T etCand date w h Recom ndat onType =>
        val target = cand date.target
        val crt = cand date.commonRecType
        val  sOonCand date = RecTypes. sOutOfNetworkT etRecType(crt) ||
          RecTypes.outOfNetworkTop cT etTypes.conta ns(crt)

        lazy val ntabD sl keCountThreshold =
          target.params(PushFeatureSw chParams.T etNtabD sl keCountThresholdParam)
        lazy val ntabD sl keRateThreshold =
          target.params(PushFeatureSw chParams.T etNtabD sl keRateThresholdParam)
        lazy val ntabD sl keCountThresholdForMrTw stly =
          target.params(PushFeatureSw chParams.T etNtabD sl keCountThresholdForMrTw stlyParam)
        lazy val ntabD sl keRateThresholdForMrTw stly =
          target.params(PushFeatureSw chParams.T etNtabD sl keRateThresholdForMrTw stlyParam)

        val  sMrTw stly = Cand dateUt l. sMrTw stlyCand date(cand date)

        lazy val d sl keCount = cand date.nu r cFeatures.getOrElse(ntabD sl keCountFeature, 0.0)
        lazy val sentCount = cand date.nu r cFeatures.getOrElse(sentFeature, 0.0)
        lazy val d sl keRate =  f (sentCount > 0) d sl keCount / sentCount else 0.0

         f (Cand dateUt l.shouldApply althQual yF lters(cand date) &&  sOonCand date) {
          allOonCand datesCounter. ncr()
          val (countThreshold, rateThreshold) =  f ( sMrTw stly) {
            (ntabD sl keCountThresholdForMrTw stly, ntabD sl keRateThresholdForMrTw stly)
          } else {
            (ntabD sl keCountThreshold, ntabD sl keRateThreshold)
          }
          cand date.cac Pred cate nfo(
            na  + "_count",
            d sl keCount,
            countThreshold,
            d sl keCount > countThreshold)
          cand date.cac Pred cate nfo(
            na  + "_rate",
            d sl keRate,
            rateThreshold,
            d sl keRate > rateThreshold)
           f (d sl keCount > countThreshold && d sl keRate > rateThreshold) {
            f lteredCand datesCounter. ncr()
            Future.False
          } else Future.True
        } else Future.True
      }
      .w hStats(stats.scope(na ))
      .w hNa (na )
  }
}
